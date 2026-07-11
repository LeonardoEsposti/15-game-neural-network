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
        GameBoard board = new GameBoard(40);
        NeuralNetwork nn = new NeuralNetwork();
        Solver solverNN = new Solver(nn);
        Solver solverIDA = new Solver();
        solverIDA.solve(board);
        solverNN.solve(board);
    }
}
