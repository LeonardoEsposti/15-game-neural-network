import exceptions.EmptyQueueException;
import game.GameBoard;
import game.Solver;
import neuralNetwork.NeuralNetwork;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {

        // --- TRAINING PHASE ---
        //NeuralNetwork nn = new NeuralNetwork();
        //Trainer.trainFromDataset("src/training/dataset.csv", nn);

        // --- TESTING PHASE ---
        GameBoard board = new GameBoard(10);
        NeuralNetwork nn = new NeuralNetwork();

        Solver solverIDA = new Solver();
        //solverIDA.solveWithIDA(board);

        Solver solverNN = new Solver(nn);
        //solverNN.solveWithIDA(board);
        solverNN.solveWithNN(board);
    }
}
