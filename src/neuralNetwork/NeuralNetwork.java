package neuralNetwork;

import dataStructures.Matrix;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Helpers {

    private final List<Layer> layers;

    public NeuralNetwork() {
        this.layers = new ArrayList<>();
        this.layers.add(new HiddenLayer(16, 256));
        this.layers.add(new HiddenLayer(256, 512));
        this.layers.add(new HiddenLayer(512, 128));
        this.layers.add(new OutputLayer(128, 1));
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

    static void main() {
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