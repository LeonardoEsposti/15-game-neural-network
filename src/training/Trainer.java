package training;

import dataStructures.Matrix;
import neuralNetwork.Layer;
import neuralNetwork.NeuralNetwork;
import game.GameBoard;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Trainer {

    private static final Matrix testing = new Matrix(new GameBoard(new int[]{1,6,2,4,5,0,10,8,9,7,3,12,13,14,11,15}));

    public static void trainFromDataset(String filepath, NeuralNetwork nn) {
        System.out.println("Loading dataset into memory...");
        ArrayList<String> dataset = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataset.add(line);
            }
        } catch (IOException e) {
            System.out.println("Could not find or read the file: " + filepath);
            return;
        }

        System.out.println("Shuffling " + dataset.size() + " boards...");
        Collections.shuffle(dataset);  // shuffles the dataset so the neural network can learn better
        System.out.println("Starting AI Training ...");
        int rowCount = 0;

        for (String line : dataset) {
            String[] element = line.split(",");
            int[] boardArray = new int[16];
            for (int i = 0; i < 16; i++) {
                boardArray[i] = Integer.parseInt(element[i]);
            }

            Matrix input = new Matrix(new GameBoard(boardArray));
            double correctDistance = Double.parseDouble(element[16]);
            Matrix target = new Matrix(1, 1);
            target.setEntry(0, 0, correctDistance);
            nn.train(input, target);
            rowCount++;

            if (rowCount % 1000 == 0) {
                saveWeights(nn);
                System.out.println("Trained on " + rowCount + " boards...");
                System.out.println("Prediction for test board (target=10): " + nn.predict(testing).getFirstEntry());
            }
        }

        System.out.println("Training Complete! Total boards processed: " + rowCount);
    }

    private static void saveWeights(NeuralNetwork nn) {
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("src/training/saved_params.csv", false))) {
            for (Layer layer : nn.layers) {
                writer.write(layer.biasesToString());
                writer.newLine();
                writer.write(layer.weigthsToString());
                writer.newLine();
            }
        } catch (java.io.IOException e) {
            System.out.println("Error while saving the weights and biases data.");
        }
    }
}