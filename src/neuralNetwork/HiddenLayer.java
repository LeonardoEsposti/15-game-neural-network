package neuralNetwork;

import dataStructures.Matrix;

public class HiddenLayer extends Layer {

    public HiddenLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public Matrix forwardPass(Matrix input) {
        this.computeValues(input);
        return relu(this.neurons);
    }
}
