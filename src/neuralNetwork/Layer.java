package neuralNetwork;

import utils.Activations;
import utils.Matrix;

public abstract class Layer implements Activations {

    protected Matrix neurons;
    protected Matrix weights;
    protected Matrix biases;

    public abstract Matrix forwardPass(Matrix input);

    public Layer(int inputSize, int outputSize) {
        this.neurons = new Matrix(inputSize, 1);
        this.weights = new Matrix(outputSize, inputSize);
        this.biases = new Matrix(outputSize, 1);
    }

    protected void computeValues(Matrix input) {
        this.neurons = input.dotProduct(this.weights).add(this.biases);
    }
}


