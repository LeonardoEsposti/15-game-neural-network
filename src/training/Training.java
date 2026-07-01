package training;

import dataStructures.Matrix;
import neuralNetwork.NeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Training {
    private static final Matrix testing = new Matrix(new int[]{2, 11, 8, 13, 5, 12, 4, 15, 0, 7, 1, 10, 14, 9, 3, 6});

    public static void trainFromDataset(String filepath, NeuralNetwork nn) {

        System.out.println("Starting AI Training...");

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int rowCount = 0;

            while ((line = reader.readLine()) != null) {

                String[] element = line.split(",");

                int[] boardArray = new int[16];
                for (int i = 0; i < 16; i++) {
                    boardArray[i] = Integer.parseInt(element[i]);
                }
                Matrix input = new Matrix(boardArray);

                double correctDistance = Double.parseDouble(element[16]);
                Matrix target = new Matrix(1, 1);
                target.setEntry(0, 0, correctDistance);

                nn.train(input, target);
                rowCount++;

                if (rowCount % 1000 == 0) {
                    System.out.println("Trained on " + rowCount + " boards...");
                    System.out.println("Prediction for board 2,11,8,13,5,12,4,15,0,7,1,10,14,9,3,6 is (target=50): " + nn.predict(testing).getFirstEntry());
                }
            }

            System.out.println("Training Complete! Total boards processed: " + rowCount);

        } catch (IOException e) {
            System.out.println("Could not find or read the file: " + filepath);
        }
    }

}
