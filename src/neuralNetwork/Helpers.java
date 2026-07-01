package neuralNetwork;

import dataStructures.Matrix;

public interface Helpers {

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

    static Matrix mse(Matrix values, Matrix target) {
        int rows = values.getNumRows();
        int cols = values.getNumCols();
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.setEntry(i, j, 0.5 * Math.pow((values.getEntry(i, j) - target.getEntry(i, j)), 2));
            }
        }
        return res;
    }

    static boolean hasIncorrectDims(Matrix m1, Matrix m2) {
        return m1.getNumRows() != m2.getNumRows() || m1.getNumCols() != m2.getNumCols();
    }
}