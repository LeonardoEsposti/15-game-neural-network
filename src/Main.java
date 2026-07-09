import dataStructures.Matrix;
import exceptions.EmptyQueueException;
import game.GameBoard;
import game.IDA_Solver;
import neuralNetwork.NeuralNetwork;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {

        int[] testBoard = {
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 0
        };

        // TRAINING NEURAL NETWORK ON DATASET
        //NeuralNetwork nn = new NeuralNetwork();
        //Trainer.trainFromDataset("src/training/dataset.csv", nn);
        //System.out.println(nn.predict(new Matrix(new GameBoard(new int[]{0,6,3,4,7,9,1,8,5,10,2,11,13,14,15,12}))).getFirstEntry());

        GameBoard board= new GameBoard(new int[]{1,6,2,10,5,0,4,3,9,13,7,8,12,14,15,11});
        NeuralNetwork nn= new NeuralNetwork();
        IDA_Solver solverNN= new IDA_Solver(nn);
        IDA_Solver solverIDA= new IDA_Solver();
        solverIDA.ida(board);
        solverNN.ida(board);



    }

}