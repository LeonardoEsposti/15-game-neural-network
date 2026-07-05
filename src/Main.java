import dataStructures.Matrix;
import exceptions.EmptyQueueException;
import game.GameBoard;
import game.IDAClass;
import game.Solver;
import neuralNetwork.NeuralNetwork;
import training.Trainer;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {
        int[] testBoard = {
                4, 3, 2, 1,
                5, 6, 11, 8,
                9, 10, 7, 12,
                13, 15, 14, 0
        };

        /*IDAClass solver = new IDAClass();
        for (int i = 0; i < 100000; i++) {
            GameBoard gameBoard = new GameBoard(10);
            solver.ida(gameBoard);
        }*/

        NeuralNetwork nn= new NeuralNetwork();
        //Trainer.trainFromDataset("./src/training/end_game_dataset.csv", nn);
        //System.out.println(nn.predict(new Matrix(new GameBoard(new int[]{1,2,3,4,5,6,7,8,9,10,0,11,13,14,15,12}))).getFirstEntry());
        Solver solver = new Solver(nn);
        solver.solve(new GameBoard(new int[]{1,9,0,4,7,2,6,3,13,15,8,10,11,14,5,12}));
    }
}