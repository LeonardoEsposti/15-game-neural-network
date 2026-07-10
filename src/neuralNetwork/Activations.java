package neuralNetwork;

import dataStructures.Matrix;

public interface Activations {

    static Matrix leakyRelu(Matrix values) {
        int rows = values.getNumRows();
        int cols = values.getNumCols();
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double val = values.getEntry(i, j);
                if (val > 0)
                    res.setEntry(i, j, val);
                else
                    res.setEntry(i, j, 0.01 * val);
            }
        }
        return res;
    }

    static Matrix leakyReluDerivative(Matrix values) {
        int rows = values.getNumRows();
        int cols = values.getNumCols();
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (values.getEntry(i, j) > 0)
                    res.setEntry(i, j, 1);
                else
                    res.setEntry(i, j, 0.01);
            }
        }
        return res;
    }
}