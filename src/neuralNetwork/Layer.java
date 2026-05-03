package neuralNetwork;

import utils.Activations;

public abstract class Layer implements Activations {
    private double[][] neurons;
    private double[][] weights;
    private double[] biases;
}
