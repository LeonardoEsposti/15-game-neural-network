package game;

import dataStructures.Matrix;
import dataStructures.Queue;
import exceptions.EmptyQueueException;
import neuralNetwork.NeuralNetwork;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {
    private final NeuralNetwork neuralNetwork;

    public Solver(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    // A helper class to track the path and score for the PriorityQueue
    private static class Node implements Comparable<Node> {
        GameBoard board;
        Node parent;
        int moveMade;
        int g; // Moves taken so far
        double f; // Total score (g + AI Prediction)

        public Node(GameBoard board, Node parent, int moveMade, int g, double f) {
            this.board = board;
            this.parent = parent;
            this.moveMade = moveMade;
            this.g = g;
            this.f = f;
        }

        public int compareTo(Node other) {
            return Double.compare(this.f, other.f);
        }
    }

    public void solve(GameBoard startBoard) throws EmptyQueueException {
        if (!startBoard.isSolvable()) {
            System.out.println("The given board is impossible to solve!");
            return;
        }

        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<GameBoard> visited = new HashSet<>();

        // Start the queue with the initial board
        double initialPrediction = neuralNetwork.predict(new Matrix(startBoard)).getFirstEntry();
        openList.add(new Node(startBoard, null, 0, 0, initialPrediction));

        System.out.println("AI is thinking... (Exploring branches)");

        while (!openList.isEmpty()) {
            // 1. Always pull the most promising board from the queue
            Node current = openList.poll();

            // 2. If it's solved, we reconstruct the path and print it!
            if (current.board.isSolved()) {
                printWinningPath(current);
                return;
            }

            // 3. Mark as visited so we don't process it twice
            if (visited.contains(current.board)) {
                continue;
            }
            visited.add(current.board);

            // 4. Generate children and evaluate them with the Neural Network
            Queue children = current.board.children();
            while (children.isNotEmpty()) {
                int move = children.readMove();
                GameBoard childBoard = children.get();

                if (!visited.contains(childBoard)) {
                    int newG = current.g + 1; // It took 1 more move to get here

                    // Ask the AI how many moves are left
                    double childPrediction = neuralNetwork.predict(new Matrix(childBoard)).getFirstEntry();

                    // f = moves taken + AI prediction of remaining moves
                    double newF = newG + childPrediction;

                    openList.add(new Node(childBoard, current, move, newG, newF));
                }
            }
        }

        System.out.println("Search exhausted. No solution found.");
    }

    // Helper to print the final path clearly
    private void printWinningPath(Node winningNode) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = winningNode;

        while (current.parent != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);

        System.out.println("\n--- SOLUTION FOUND IN " + path.size() + " MOVES ---");
        for (Node n : path) {
            System.out.println("Move: " + getMove(n.moveMade));
            n.board.printBoard();
            System.out.println();
        }
    }

    private static String getMove(int move) {
        return switch (move) {
            case 1 -> "UP";
            case 2 -> "RIGHT";
            case 3 -> "DOWN";
            case 4 -> "LEFT";
            default -> "ERROR";
        };
    }
}