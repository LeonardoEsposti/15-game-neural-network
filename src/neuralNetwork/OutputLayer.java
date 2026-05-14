package neuralNetwork;

import utils.Matrix;

public class OutputLayer extends Layer {

    public OutputLayer(int inputSize, int outputSize) {
        super(inputSize, outputSize);
        this.initWeights(1); // Xavier/Glorot initialization (perfect for softmax)
    }

    public Matrix forwardPass(Matrix input) {
        computeValues(input);
        return softmax(this.neurons);
    }

    static void main() {
        OutputLayer ol = new OutputLayer(6, 4);

        double[][] a = {{1}, {2}, {3}, {4}, {5}, {6}};
        Matrix m1 = new Matrix(a);

        // TEST: softmax activation
        Matrix next = ol.forwardPass(m1);
        ol.neurons.printMatrix();
        System.out.println();
        next.printMatrix();
    }
}

