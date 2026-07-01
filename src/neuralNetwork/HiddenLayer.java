package neuralNetwork;

import dataStructures.Matrix;

class HiddenLayer extends Layer {

    HiddenLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    protected Matrix forwardPass(Matrix input) {
        this.input = input;
        this.computeValues();
        return Helpers.leakyRelu(this.values);
    }
}
