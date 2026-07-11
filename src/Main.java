import exceptions.EmptyQueueException;
import game.GameBoard;
import game.Solver;
import neuralNetwork.NeuralNetwork;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {

        Scanner sc = new Scanner(System.in);
        String readLine;
        NeuralNetwork nn = new NeuralNetwork(false);
        Solver solverNN = new Solver(nn);

        // --- TRAINING PHASE ---
        //NeuralNetwork nn = new NeuralNetwork();
        //Trainer.trainFromDataset("src/training/dataset.csv", nn);

        // --- TESTING PHASE ---
        System.out.println("The new scrambled board is: \n");
        GameBoard board = new GameBoard(100);
        board.printBoard();

        System.out.println("\nNow some tests to show Neural Network Prediction accuracy: ");

        while (true) {
            Solver comparisonSolver = new Solver(nn, true);
            comparisonSolver.predictionComparison(board);
            readLine = sc.nextLine();
            if (readLine.equals("stop"))
                break;
            board = new GameBoard(100);
            board.printBoard();
            System.out.println();
        }

        System.out.println("IDA* solve: ");
        readLine = sc.nextLine();
        if (!readLine.equals("skip")) {
            Solver solverIDA = new Solver();
            solverIDA.solveWithIDA(board);
        }

        System.out.println("Solve attempt with Neural Network only: ");
        readLine = sc.nextLine();
        if (!readLine.equals("skip"))
            solverNN.solveWithNN(board);

        System.out.println("Solve with IDA* + Neural Network (on an easier board): ");
        GameBoard easyGameBoard = new GameBoard(20);
        readLine = sc.nextLine();
        if (!readLine.equals("skip"))
            solverNN.solveWithIDA(easyGameBoard);
    }
}