package neuralNetwork;

import dataStructures.Matrix;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NeuralNetwork implements Activations {

    public final ArrayList<Layer> layers;
    private final boolean INITIALIZATION = false;
    private final String filepath = "src/training/saved_params.csv";

    public NeuralNetwork() {
        this.layers = new ArrayList<>();
        if (INITIALIZATION) {
            this.layers.add(new HiddenLayer(256, 512));
            this.layers.add(new HiddenLayer(512, 256));
            this.layers.add(new HiddenLayer(256, 128));
            this.layers.add(new OutputLayer(128, 1));
        } else this.loadInfo();
    }

    public Matrix predict(Matrix input) {
        Matrix values = input;
        for (Layer layer : this.layers)
            values = layer.forwardPass(values);
        return values;
    }

    public void train(Matrix input, Matrix target) {
        final double learningRate = 0.0001;
        Matrix prediction = this.predict(input);
        Matrix error = prediction.subtract(target);
        for (int i = this.layers.size() - 1; i >= 0; i--) {
            Layer layer = this.layers.get(i);
            error = layer.backwardPass(error, learningRate);
        }
    }

    // saved weights format: rows, cols, entry(1x1),entry(1x2),....,entry(rxc)
    // saved biases format: rows, 0, entry1,entry2,......,entryR
    private void loadInfo() {
        int cols = 0, rows = 0, numHiddenLayers = 0;
        Matrix savedWeights = null, savedBiases = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (int k = 0; k < 2; k++) {

                    if (k == 1) line = reader.readLine();

                    String[] element = line.split(",");
                    rows = Integer.parseInt(element[0]);

                    if (k == 0) cols = 1;
                    else cols = Integer.parseInt(element[1]);

                    double[][] newMatrix = new double[rows][cols];
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            newMatrix[i][j] = Double.parseDouble(element[i*cols+j+2]);
                        }
                    }

                    if (k == 0) savedBiases = new Matrix(newMatrix);
                    else savedWeights = new Matrix(newMatrix);
                }

                if (numHiddenLayers < 3) {
                    this.layers.add(new HiddenLayer(cols, rows, savedWeights, savedBiases));
                    numHiddenLayers++;
                } else {
                    this.layers.add(new OutputLayer(cols, rows, savedWeights, savedBiases));
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not find or read the file: " + this.filepath);
        }
    }

    public static void main(String[] args) {

        // TEST: backward propagation
        NeuralNetwork nn = new NeuralNetwork();
        double[][] in = {{5}, {1}, {2}, {4}, {9}, {7}, {3}, {8}, {13}, {6}, {10}, {12}, {14}, {0}, {11}, {15}};
        Matrix input = new Matrix(in);
        nn.predict(input).printMatrix();

        double[][] out = {{12}};
        Matrix target = new Matrix(out);

        for (int i = 0; i < 1000; i++) {
            nn.train(input, target);
            if (i % 20 == 0) {
                System.out.println();
                nn.predict(input).printMatrix();
            }
        }
    }
}