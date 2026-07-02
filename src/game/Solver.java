package game;

import dataStructures.Matrix;
import dataStructures.Queue;
import exceptions.EmptyQueueException;
import neuralNetwork.NeuralNetwork;

public class Solver {
    private final NeuralNetwork neuralNetwork;

    public Solver(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public void solve(GameBoard board) throws EmptyQueueException {
        Queue children;
        GameBoard parentBoard = board;
        int moveToNotInclude = 0;
        while (!parentBoard.isSolved()) {
            double minPrediction = Double.MAX_VALUE;
            int smallestMove = 0;
            GameBoard smallestBoard = null;

            children = parentBoard.children(moveToNotInclude);

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
            System.out.println("Next move is " + getMove(smallestMove) + " with a prediction of: " + minPrediction);
            moveToNotInclude = (smallestMove + 1) % 4 + 1;
            smallestBoard.printBoard();
            parentBoard = smallestBoard;
        }
    }

    public static String getMove(int move) {
        return switch (move) {
            case 1 -> "UP";
            case 2 -> "RIGHT";
            case 3 -> "DOWN";
            case 4 -> "LEFT";
            default -> "ERROR";
        };
    }
}
