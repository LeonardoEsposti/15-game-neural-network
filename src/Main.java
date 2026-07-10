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
        GameBoard board = new GameBoard(10);
        NeuralNetwork nn = new NeuralNetwork();
        IDA_Solver solverNN = new IDA_Solver(nn);
        IDA_Solver solverIDA = new IDA_Solver();
        solverIDA.ida(board);
        solverNN.ida(board);
    }
}