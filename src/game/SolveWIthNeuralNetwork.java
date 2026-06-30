package game;

import dataStructures.Matrix;
import dataStructures.Queue;
import exceptions.EmptyQueueException;
import neuralNetwork.NeuralNetwork;

public class SolveWIthNeuralNetwork {
    public static void solve(GameBoard board) throws EmptyQueueException {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        Queue children;
        GameBoard parentBoard = board;

        while (!parentBoard.isComplete()) {
            double minPrediction = Double.MAX_VALUE;
            int smallestMove = 0;
            GameBoard smallestBoard = null;

            children = parentBoard.Children();

            while (children.isNotEmpty()) {
                int moved = children.readMove();
                GameBoard child = children.get();
                double childPrediction = neuralNetwork.predict(new Matrix(child)).getFirstEntry();

                if (childPrediction < minPrediction) {
                    minPrediction = childPrediction;
                    smallestMove = moved;
                    smallestBoard = child;
                }
            }
            System.out.println("Next move is " + getMove(smallestMove));
            smallestBoard.printBoard();
            parentBoard = smallestBoard;
        }
    }

    public static String getMove(int move) {
        return switch (move) {
            case 1 -> "UP";
            case 2 -> "LEFT";
            case 3 -> "DOWN";
            case 4 -> "RIGHT";
            default -> "ERROR";
        };
    }
}
