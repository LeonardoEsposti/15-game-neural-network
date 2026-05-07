package neuralNetwork;

import utils.Activations;

public abstract class Layer implements Activations {

    double[] neurons;
    double[][] weights;
    double[] biases;

    public abstract void forwardPass();

    public Layer(double[] neurons, double[][] weights, double[] biases) {
        this.neurons = neurons;
        this.weights = weights;
        this.biases = biases;
    }

    public void computeValues() {
        // z = w * n + b
    }
}


