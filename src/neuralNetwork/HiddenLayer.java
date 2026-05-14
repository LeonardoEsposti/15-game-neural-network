package neuralNetwork;

import utils.Matrix;

public class HiddenLayer extends Layer {

    public HiddenLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
        this.initWeights(2); // He initialization (perfect for relu)
    }

    public Matrix forwardPass(Matrix input) {
        this.computeValues(input);
        return relu(this.neurons);
    }

    static void main() {

        HiddenLayer hl = new HiddenLayer(3, 5);
        double[][] a = {{1}, {2}, {3}};
        Matrix m1 = new Matrix(a);

        // TEST: relu activation
        Matrix next = hl.forwardPass(m1);
        hl.neurons.printMatrix();
        System.out.println();
        next.printMatrix();
    }
}
