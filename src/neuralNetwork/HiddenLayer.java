package neuralNetwork;

import utils.Matrix;

public class HiddenLayer extends Layer {

    public HiddenLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public Matrix forwardPass(Matrix input) {
        computeValues(input);
        return relu(input);
    }
}
