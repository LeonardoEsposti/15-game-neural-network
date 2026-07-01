import dataStructures.Matrix;
import exceptions.EmptyQueueException;
import game.GameBoard;
import game.IDA_Class;
import game.SolveWIthNeuralNetwork;
import neuralNetwork.NeuralNetwork;
import training.Training;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {
        int[] testBoard = {
                4, 3, 2, 1,
                5, 6, 11, 8,
                9, 10, 7, 12,
                13, 15, 14, 0
        };
        /*IDA_Class solver = new IDA_Class();
        while(true){
            GameBoard gameBoard = new GameBoard(170);
            solver.ida(gameBoard);
        }*/
        NeuralNetwork nn= new NeuralNetwork();
        Training.trainFromDataset("training_data.csv",nn);
        SolveWIthNeuralNetwork solver=  new SolveWIthNeuralNetwork(nn);
        solver.solve(new GameBoard(new int[]{2,11,8,13,5,12,4,15,0,7,1,10,14,9,3,6}));
    }
}