package neuralNetwork;

import utils.Activations;
import utils.Matrix;
import java.util.Random;

public abstract class Layer implements Activations {

    protected Matrix neurons;  // vector n rows
    protected Matrix weights;  // matrix k rows, n col
    protected Matrix biases;   // vector k rows

    public abstract Matrix forwardPass(Matrix input);

    public Layer(int inputSize, int outputSize) {
        this.neurons = new Matrix(inputSize, 1);
        this.weights = new Matrix(outputSize, inputSize);
        this.biases = new Matrix(outputSize, 1);
        this.biases.fill(0);
    }

    protected void initWeights(double numerator) {
        int n = this.weights.getNumCols();
        double scale = Math.sqrt(numerator / n);
        for (int i = 0; i < this.weights.getNumRows(); i++) {
            for (int j = 0; j < n; j++) {
                this.weights.setEntry(i, j, new Random().nextGaussian() * scale);
            }
        }
    }

    protected void computeValues(Matrix input) {
        this.neurons = this.weights.multiply(input).add(this.biases);
    }
}


