package neuralNetwork;

import utils.Matrix;

public class OutputLayer extends Layer {

    public OutputLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public Matrix forwardPass(Matrix input) {
        computeValues(input);
        return softmax(input);
    }
}

