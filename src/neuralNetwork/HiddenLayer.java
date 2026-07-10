package neuralNetwork;

import dataStructures.Matrix;

class HiddenLayer extends Layer {

    HiddenLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    HiddenLayer(int inputSize, int outputSize, Matrix savedWeights, Matrix savedBiases) {
        super(inputSize, outputSize, savedWeights, savedBiases);
    }

    protected Matrix forwardPass(Matrix input) {
        this.input = input;
        this.computeValues();
        return Activations.leakyRelu(this.values);
    }
}