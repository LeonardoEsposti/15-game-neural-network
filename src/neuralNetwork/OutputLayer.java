package neuralNetwork;

import dataStructures.Matrix;

class OutputLayer extends Layer {

    OutputLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    OutputLayer(int inputSize, int outputSize, Matrix savedWeights, Matrix  savedBiases) {
        super(inputSize, outputSize, savedWeights, savedBiases);
    }
    protected Matrix forwardPass(Matrix input) {
        this.input = input;
        this.computeValues();
        return Helpers.leakyRelu(this.values);
    }
}
