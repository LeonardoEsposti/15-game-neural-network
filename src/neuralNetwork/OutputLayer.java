package neuralNetwork;

import dataStructures.Matrix;

public class OutputLayer extends Layer {

    private Matrix output;

    OutputLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    protected Matrix forwardPass(Matrix input) {
        this.input = input;
        this.computeValues();
        this.output = relu(this.values);
        return this.output;
    }
}
