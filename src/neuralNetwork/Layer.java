package neuralNetwork;

import dataStructures.Matrix;
import java.util.Random;

public abstract class Layer implements Functions {

    protected Matrix neurons;  // n x 1 matrix
    protected Matrix weights;  // k x n matrix
    protected Matrix biases;   // k x 1 matrix

    public abstract Matrix forwardPass(Matrix input);

    public Layer(int inputSize, int outputSize) {
        this.neurons = new Matrix(inputSize, 1);
        this.weights = new Matrix(outputSize, inputSize);
        this.biases = new Matrix(outputSize, 1);
        this.initWeights();
        this.biases.fill(0);
    }

    protected void initWeights() {
        int n = this.weights.getNumCols();
        double scale = Math.sqrt(2.0 / n);
        for (int i = 0; i < this.weights.getNumRows(); i++) {
            for (int j = 0; j < n; j++) {
                this.weights.setEntry(i, j, new Random().nextGaussian() * scale);  // He initialization
            }
        }
    }

    protected void computeValues(Matrix input) {
        this.neurons = this.weights.multiply(input).add(this.biases);  // Z = W * X + B
    }
}


