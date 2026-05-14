package utils;

public interface Activations {

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

    default Matrix softmax(Matrix values) {
        int rows = values.getNumRows();
        int cols = values.getNumCols();
        Matrix res = new Matrix(rows, cols);
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += Math.exp(values.getEntry(i, j));
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.setEntry(i, j, Math.exp(values.getEntry(i, j)) / sum);
            }
        }
        return res;
    }
}