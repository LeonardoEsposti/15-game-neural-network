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

    static void main() {
        HiddenLayer hl = new HiddenLayer(3, 1);
        // TODO: testare se relu funziona correttamente
    }
}
