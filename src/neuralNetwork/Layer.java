package neuralNetwork;

import utils.Activations;
import utils.Matrix;

public abstract class Layer implements Activations {

    protected Matrix neurons;
    protected Matrix weights;
    protected Matrix biases;

    public abstract Matrix forwardPass(Matrix input);

    private void init() {
        // TODO: inizializzare pesi
        this.biases.fill(0);
    }

    public Layer(int inputSize, int outputSize) {
        this.neurons = new Matrix(inputSize, 1);
        this.weights = new Matrix(outputSize, inputSize);
        this.biases = new Matrix(outputSize, 1);
        this.init();
    }

    protected void computeValues(Matrix input) {
        this.neurons = input.multiply(this.weights).add(this.biases);
    }
}


