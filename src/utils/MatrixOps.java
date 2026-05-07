package utils;

public interface MatrixOps {

    default double[] addVectors(double[] vectorA, double[] vectorB) {
        int length = vectorA.length;
        double[] res = new double[length];

        for (int i = 0; i < length; i++)
            res[i] = vectorA[i] + vectorB[i];

        return res;
    }

    default double[] matrixVectorProduct(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] res = new double[rows];

        for (int i = 0; i < rows; i++) {
            double newRow = 0;
            for (int j = 0; j < cols; j++) {
                newRow += (matrix[i][j] * vector[j]);
            }
            res[i] = newRow;
        }

        return res;
    }
}
