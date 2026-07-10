import dataStructures.Matrix;
import exceptions.EmptyQueueException;
import game.GameBoard;
import game.IDA_Solver;
import neuralNetwork.NeuralNetwork;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {

        // --- TRAINING PHASE ---
        //NeuralNetwork nn = new NeuralNetwork();
        //Trainer.trainFromDataset("src/training/dataset.csv", nn);

        // --- TESTING PHASE ---
        GameBoard board = new GameBoard(new int[]{1,5,2,3,6,0,8,4,9,13,7,12,14,11,10,15});
        NeuralNetwork nn = new NeuralNetwork();
        IDA_Solver solverNN = new IDA_Solver(nn);
        IDA_Solver solverIDA = new IDA_Solver();
        solverIDA.ida(board);
        solverNN.ida(board);
    }
}