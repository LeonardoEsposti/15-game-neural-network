package neuralNetwork;

import dataStructures.Matrix;

public class OutputLayer extends Layer {

    OutputLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    protected Matrix forwardPass(Matrix input) {
        this.input = input;
        this.computeValues();

        return leakyRelu(this.values);
    }
}
