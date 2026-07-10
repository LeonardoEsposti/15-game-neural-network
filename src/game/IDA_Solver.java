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

public class IDA_Solver implements ReverseScramble {

    private final boolean withNeuralNetwork;
    private boolean saveOnFile;
    private NeuralNetwork nn = null;
    private final HashMap<GameBoard, Integer> data = ReverseScramble.calculate(15);
    private static final HashSet<String> alreadySaved = new HashSet<>();  // avoids repetitions
    private final HashMap<GameBoard, Double> nnCache = new HashMap<>();  // to take work off the cpu during nn solving

    public IDA_Solver() {
        this.withNeuralNetwork = false;
    }

    public IDA_Solver(boolean saveOnFile) {
        this.withNeuralNetwork = false;
        this.saveOnFile = true;
    }

    public IDA_Solver(NeuralNetwork nn) {
        this.withNeuralNetwork = true;
        this.nn = nn;
    }

    // checks the expected future of a board
    private double f(GameBoard board, int g, double bound, ArrayList<GameBoard> path) throws EmptyQueueException {
        double h;

        if (this.withNeuralNetwork) {
            if (!nnCache.containsKey(board))
                nnCache.put(board, nn.predict(new Matrix(board)).getFirstEntry());
            h =  nnCache.get(board);
        } else {
            if (this.data.containsKey(board)) h = this.data.get(board);
            else h = board.heuristic();
        }

        double f = g + h;
        if (f > bound) return f;
        if ((this.data.containsKey(board) && !withNeuralNetwork) || board.isSolved()) return -1;

        double min = Double.MAX_VALUE;
        Queue children = board.children();
        while (children.isNotEmpty()) {
            GameBoard child = children.get();
            if (path.contains(child)) continue;
            path.add(child);
            double t = f(child, g + 1, bound, path);
            if (t == -1) return -1;
            if (t < min) min = t;
            path.removeLast();
        }
        return min;
    }

    public void ida(GameBoard board) throws EmptyQueueException
    {
        if (!board.isSolvable()) {
            System.out.println("Board is not mathematically solvable!");
            return;
        }

        double t, bound;
        if (!this.saveOnFile)
            System.out.print("Solving Board with ");
        if (this.withNeuralNetwork) {
            System.out.println("Neural Network...");
            nnCache.put(board, nn.predict(new Matrix(board)).getFirstEntry());
            bound = nnCache.get(board);
        } else {
            System.out.println("IDA algorithm...");
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
            System.out.println();
            switch (b.getCoords() - next.getCoords()) {
                case -1 -> System.out.println("RIGHT (>)");
                case 1 -> System.out.println("LEFT (<)");
                case -4 -> System.out.println("DOWN (v)");
                case 4 -> System.out.println("UP (^)");
                default -> System.out.println("Error while printing the path.");
            }
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
}