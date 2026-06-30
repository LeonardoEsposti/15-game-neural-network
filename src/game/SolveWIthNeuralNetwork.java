package game;

import dataStructures.Matrix;
import dataStructures.Queue;
import exceptions.EmptyQueueException;
import neuralNetwork.*;

public class SolveWIthNeuralNetwork {
    public static void solve(GameBoard board) throws EmptyQueueException {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        double prediction= neuralNetwork.predict(new Matrix(board)).getFirstEntry();
        Queue children;
        GameBoard closestChild=board;
        while(!closestChild.isComplete()){
            children= closestChild.Children();
            for (int i=0;i<4;i++) { //AT MOST 4 TIMES
                int moved = children.readMove();
                GameBoard child = children.get();
                if (neuralNetwork.predict(new Matrix(child)).getFirstEntry() < prediction) {
                    System.out.println("Next move is " + moved);
                    child.printBoard();
                    closestChild = child;
                    break;
                }
            }
        }
    }
}
