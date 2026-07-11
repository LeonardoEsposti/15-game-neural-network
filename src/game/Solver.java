package game;

import dataStructures.Matrix;
import dataStructures.Queue;
import exceptions.EmptyQueueException;
import neuralNetwork.NeuralNetwork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Solver implements ReverseScramble, Moves {

    private boolean withNeuralNetwork = false;
    private boolean saveOnFile = false;
    private NeuralNetwork nn = null;
    private boolean showComparison;

    private final HashMap<GameBoard, Integer> data = ReverseScramble.calculate(15);
    private static final HashSet<String> alreadySaved = new HashSet<>();  // avoids repetitions
    private final HashMap<GameBoard, Double> nnCache = new HashMap<>(); // takes work off the cpu during nn predictions
    private final static NeuralNetwork dumbNN = new NeuralNetwork(true);

    public Solver() {
        this.withNeuralNetwork = false;
    }

    public Solver(boolean saveOnFile) {
        this.withNeuralNetwork = false;
        this.saveOnFile = saveOnFile;
    }

    public Solver(NeuralNetwork nn, boolean showComparison) {
        this.nn = nn;
        this.showComparison = showComparison;
    }

    public Solver(NeuralNetwork nn) {
        this.withNeuralNetwork = true;
        this.nn = nn;
    }

    // checks the expected future of a board
    private double f(GameBoard board, int g, double bound, ArrayList<GameBoard> path) throws EmptyQueueException {
        double h;

        if (this.withNeuralNetwork) {
            if (!nnCache.containsKey(board))
                nnCache.put(board, this.nn.predict(new Matrix(board)).getFirstEntry());
            h = nnCache.get(board);
        } else {
            if (this.data.containsKey(board)) h = this.data.get(board);
            else h = board.heuristic();
        }

        double f = g + h;
        if (f > bound) return f;
        if ((this.data.containsKey(board) && !this.withNeuralNetwork) || board.isSolved()) return -1;

        double min = Double.MAX_VALUE;
        Queue children = board.children();
        while (children.isNotEmpty()) {
            GameBoard child = children.get();
            if (path.contains(child)) continue;
            path.add(child);
            double t = f(child, g + 1, bound, path);
            if (t == -1) return t;
            if (t < min) min = t;
            path.removeLast();
        }
        return min;
    }

    public void solveWithIDA(GameBoard board) throws EmptyQueueException {
        if (!board.isSolvable()) {
            System.out.println("Board is not mathematically solvable!");
            return;
        }

        double t, bound;
        if (!this.saveOnFile && !showComparison)
            System.out.print("Solving board with IDA algorithm using ");
        if (this.withNeuralNetwork) {
            System.out.println("neural network prediction...\n");
            nnCache.put(board, nn.predict(new Matrix(board)).getFirstEntry());
            bound = nnCache.get(board);
        } else {
            if (!showComparison) System.out.println("pure IDA...\n");
            if (this.data.containsKey(board)) bound = this.data.get(board);
            else bound = board.heuristic();
        }

        ArrayList<GameBoard> path;
        while (true) {
            path = new ArrayList<>();
            path.add(board);
            t = f(board, 0, bound, path);
            if (t != -1) {
                bound = t;
                continue;
            }
            break;
        }

        if (this.withNeuralNetwork) {
            this.printPath(path);
            nnCache.clear();
            return;
        }

        // reconstructing the whole path
        GameBoard current = path.getLast();
        int distance = this.data.get(current);

        //if we only need comparison
        if (showComparison) {
            System.out.println((distance + path.size() - 1));
            return;
        }


        while (distance > 0) {
            Queue children = current.children();
            while (children.isNotEmpty()) {
                GameBoard child = children.get();
                if (this.data.containsKey(child) && this.data.get(child) == distance - 1) {
                    path.add(child);
                    current = child;
                    distance--;
                    break;
                }
            }
        }

        if (this.saveOnFile)
            this.save(path);
        else
            this.printPath(path);
    }

    private void printPath(ArrayList<GameBoard> path) {
        GameBoard b = null;
        GameBoard next = null;
        for (int i = 0; i < path.size() - 1; i++) {
            b = path.get(i);
            b.printBoard();
            next = path.get(i + 1);
            System.out.println("\nNext move is " + getMoveByOffset(next.getCoords() - b.getCoords()) + "\n");
        }

        // confirmation
        if (next.isSolved()) {
            next.printBoard();
            System.out.println("\n--WIN-- IN ONLY " + (path.size() - 1) + " MOVES\n");
        }
    }

    private void save(ArrayList<GameBoard> path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/training/dataset.csv", true))) {
            int distance = path.size() - 1;
            for (int i = 0; i < path.size(); i++) {
                GameBoard b = path.get(i);
                String boardHash = b.toString();
                if (!alreadySaved.contains(boardHash)) {
                    int trueDistance = distance - i;
                    writer.write(b.toString() + trueDistance);
                    writer.newLine();
                    alreadySaved.add(boardHash);
                }
            }
            System.out.println("Path solved and board written in the dataset.");
        } catch (IOException e) {
            System.out.println("Error while saving the training data.");
        }
    }

    public void solveWithNN(GameBoard board) throws EmptyQueueException {
        if (!withNeuralNetwork) {
            System.out.println("No neural network provided.");
            return;
        } else if (showComparison) return;

        if (!board.isSolvable()) {
            System.out.println("Board is not mathematically solvable!");
            return;
        }
        System.out.println("Solving board with neural network...\n");
        board.printBoard();
        Queue children;
        GameBoard parent = board;
        int moveToExclude = 0;

        int iterations = 0;
        final int IT_LIMIT = 100;
        while (!parent.isSolved()) {
            iterations++;
            double minPrediction = Double.MAX_VALUE;
            int minMove = 0;
            GameBoard minBoard = null;

            children = parent.children(moveToExclude);
            while (children.isNotEmpty()) {
                int move = children.readMove();
                GameBoard child = children.get();
                double prediction = nn.predict(new Matrix(child)).getFirstEntry();

                if (prediction < minPrediction) {
                    minPrediction = prediction;
                    minMove = move;
                    minBoard = child;
                }
            }
            System.out.println("\nNext move is " + getMove(minMove) + ", with a prediction of " + minPrediction + "\n");
            moveToExclude = (minMove + 1) % 4 + 1;
            minBoard.printBoard();
            parent = minBoard;
            if (iterations > IT_LIMIT) {
                System.out.println("The iteration limit has been reached.");
                return;
            }
        }
    }

    public void predictionComparison(GameBoard board) throws EmptyQueueException {
        if (!showComparison) return;
        System.out.print("-Matematically perfect expected amount of moves (calculated with IDA*) is: ");
        solveWithIDA(board);
        System.out.println("-The trained Neural Network predicted: " + nn.predict(new Matrix(board)).getFirstEntry());
        System.out.println("-The NOT trained Neural Network predicted: " + dumbNN.predict(new Matrix(board)).getFirstEntry());
    }
}