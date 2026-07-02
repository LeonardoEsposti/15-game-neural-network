import exceptions.EmptyQueueException;
import game.GameBoard;
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
        /* IDAClass solver = new IDAClass();
        while(true) {
            GameBoard gameBoard = new GameBoard(170);
            solver.ida(gameBoard);
        } */
        NeuralNetwork nn= new NeuralNetwork();
        Trainer.trainFromDataset("./src/training/dataset.csv", nn);
        Solver solver = new Solver(nn);
        solver.solve(new GameBoard(new int[]{2,11,8,13,5,12,4,15,0,7,1,10,14,9,3,6}));
    }
}