package utils;

public class Matrix {
    private final int rows, cols;
    private final double[][] entries;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.entries = new double[rows][cols];
    }

    public Matrix(double[][] entries) {
        this.rows = entries.length;
        this.cols = entries[0].length;
        this.entries = entries;
    }

    public int getNumRows() {
        return this.rows;
    }

    public int getNumCols() {
        return this.cols;
    }

    public double getEntry(int row, int col) {
        return this.entries[row][col];
    }

    public void setEntry(int row, int col, double newValue) {
        this.entries[row][col] = newValue;
    }

    public Matrix add(Matrix m) throws IllegalArgumentException {
        if (this.rows != m.rows || this.cols != m.cols)
            throw new IllegalArgumentException("Cannot add matrices of different dimensions!");

        Matrix res = new Matrix(this.rows, this.cols);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                res.entries[i][j] = this.entries[i][j] + m.entries[i][j];
            }
        }
        return res;
    }

    public Matrix dotProduct(Matrix m) throws IllegalArgumentException {
        if (this.cols != m.rows)
            throw new IllegalArgumentException("Cannot multiply matrices of non-suitable dimensions!");

        Matrix res = new Matrix(this.rows, m.cols);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                double entry = 0;
                for (int k = 0; k < this.cols; k++) {
                    entry += (this.entries[i][k] * m.entries[k][j]);
                }
                res.entries[i][j] = entry;
            }
        }
        return res;
    }

    public void printMatrix() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print(this.entries[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main() {
        double[][] a = {{1, 2}, {3, 4}, {5, 6}};
        Matrix m1 = new Matrix(a); // 3 x 2

        double[][] b = {{5, 6}, {7, 8}, {9, 10}};
        Matrix m2 = new Matrix(b); // 3 x 2

        try {
            m1.add(m2).printMatrix();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println();

        double[][] c = {{1, 2, 3}, {4, 5, 6}};
        Matrix m3 = new Matrix(c); // 2 x 3

        try {
            m3.dotProduct(m1).printMatrix();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
