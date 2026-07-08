import dataStructures.Matrix;
import exceptions.EmptyQueueException;
import game.GameBoard;
import game.Generator;
import game.Solver;
import training.Trainer;
import neuralNetwork.NeuralNetwork;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {

        int[] testBoard = {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 0
        };

        // GENERATING DATASET
        /* Generator generator = new Generator();
        for (int i = 0; i < 100000; i++) {
            GameBoard gameBoard = new GameBoard(10);
            generator.ida(gameBoard);
        } */

        // TRAINING NEURAL NETWORK ON DATASET
        NeuralNetwork nn = new NeuralNetwork();
        //Trainer.trainFromDataset("src/training/dataset.csv", nn);
        //System.out.println(nn.predict(new Matrix(new GameBoard(new int[]{1,2,3,4,5,6,7,8,9,10,0,11,13,14,15,12}))).getFirstEntry());

        // TESTING NEURAL NETWORK ON NEW GAMEBOARDS
        Solver solver = new Solver(nn);
        solver.solve(new GameBoard(50));
    }
}