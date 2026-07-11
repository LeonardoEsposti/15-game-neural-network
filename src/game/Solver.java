package game;

import dataStructures.Matrix;
import dataStructures.Queue;
import exceptions.EmptyQueueException;
import neuralNetwork.NeuralNetwork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Solver implements ReverseScramble {

    private final boolean withNeuralNetwork;
    private boolean saveOnFile = false;
    private NeuralNetwork nn = null;
    private final HashMap<GameBoard, Integer> data = ReverseScramble.calculate(15);
    private static final HashSet<String> alreadySaved = new HashSet<>();  // avoids repetitions

    public Solver() {
        this.withNeuralNetwork = false;
    }

    public Solver(boolean saveOnFile) {
        this.withNeuralNetwork = false;
        this.saveOnFile = saveOnFile;
    }

    public Solver(NeuralNetwork nn) {
        this.withNeuralNetwork = true;
        this.nn = nn;
    }

    // checks the expected future of a board
    private double f(GameBoard board, int g, double bound, ArrayList<GameBoard> path) throws EmptyQueueException {
        double h;
        if (this.data.containsKey(board)) h = this.data.get(board);
        else h = board.heuristic();

        double f = g + h;
        if (f > bound) return f;
        if (this.data.containsKey(board) || board.isSolved()) return -1;

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

    private void ida(GameBoard board) throws EmptyQueueException {
        double t, bound;

        if (this.data.containsKey(board)) bound = this.data.get(board);
        else bound = board.heuristic();

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

        // reconstructing the whole path
        GameBoard current = path.getLast();
        int distance = this.data.get(current);
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
            System.out.println("\n--WIN-- IN ONLY " + (path.size() - 1) + " MOVES");
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

    public void solve(GameBoard board) throws EmptyQueueException {
        if (!board.isSolvable()) {
            System.out.println("Board is not mathematically solvable!");
            return;
        }
        if (!this.saveOnFile)
            System.out.print("Solving Board with ");
        if (this.withNeuralNetwork) {
            System.out.println("Neural Network...\n");
            this.solveWithNeuralNetwork(board);
        } else {
            System.out.println("IDA algorithm...\n");
            this.ida(board);
        }
    }

    private void solveWithNeuralNetwork(GameBoard board) throws EmptyQueueException {
        Queue children;
        GameBoard parent = board;
        int moveToExclude = 0;
        while (!parent.isSolved()) {
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
            System.out.println("\nNext move is " + getMove(minMove) + ", with a prediction of " + (int) minPrediction + " moves\n");
            moveToExclude = (minMove + 1) % 4 + 1;
            minBoard.printBoard();
            parent = minBoard;
        }
    }

    private static String getMove(int move) {
        return switch (move) {
            case 1 -> "UP (^)";
            case 2 -> "RIGHT (>)";
            case 3 -> "DOWN (v)";
            case 4 -> "LEFT (<)";
            default -> "ERROR";
        };
    }

    private static String getMoveByOffset(int offset) {
        return switch (offset) {
            case -4 -> "UP (^)";
            case 1 -> "RIGHT (>)";
            case 4 -> "DOWN (v)";
            case -1 -> "LEFT (<)";
            default -> "ERROR";
        };
    }
}