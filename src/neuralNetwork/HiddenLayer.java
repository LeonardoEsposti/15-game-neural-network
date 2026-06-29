package neuralNetwork;

import dataStructures.Matrix;

public class HiddenLayer extends Layer {

    HiddenLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    protected Matrix forwardPass(Matrix input) {
        this.input = input;
        this.computeValues();
        return relu(this.values);
    }
}
