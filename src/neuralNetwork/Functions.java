package neuralNetwork;

import dataStructures.Matrix;

public interface Functions {

    default Matrix relu(Matrix values) {
        int rows = values.getNumRows();
        int cols = values.getNumCols();
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.setEntry(i, j, Math.max(0, values.getEntry(i, j)));
            }
        }
        return res;
    }

    default Matrix reluDerivative(Matrix values) {
        int rows = values.getNumRows();
        int cols = values.getNumCols();
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (values.getEntry(i, j) > 0)
                    res.setEntry(i, j, 1);
                else
                    res.setEntry(i, j, 0);
            }
        }
        return res;
    }

    default Matrix mse(Matrix values, Matrix target) {
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
}