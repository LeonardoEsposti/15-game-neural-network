package neuralNetwork;

import dataStructures.Matrix;

public class OutputLayer extends Layer {

    public OutputLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public Matrix forwardPass(Matrix input) {
        this.computeValues(input);
        return relu(this.neurons);
    }
}

