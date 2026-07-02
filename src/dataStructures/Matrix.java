package dataStructures;

import game.GameBoard;
import neuralNetwork.Helpers;

public class Matrix implements Helpers {

    private final int rows, cols;
    private final double[][] entries;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.entries = new double[rows][cols];
    }

    public Matrix(double[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.entries = matrix;
    }

    public Matrix(int[] board) {
        this(board.length, 1);
        for (int i = 0; i < this.rows; i++)
            this.entries[i][0] = board[i];
    }

    public Matrix(GameBoard gb) {
        this(gb.toOneHotEncoding());
    }

    public int getNumRows() {
        return this.rows;
    }

    public int getNumCols() {
        return this.cols;
    }

    public double getFirstEntry() {
        return this.entries[0][0];
    }

    public double getEntry(int row, int col) {
        return this.entries[row][col];
    }

    public void setEntry(int row, int col, double value) {
        this.entries[row][col] = value;
    }

    public void fill(double value) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.entries[i][j] = value;
            }
        }
    }

    public Matrix transpose() {
        Matrix res = new Matrix(this.cols, this.rows);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                res.entries[i][j] = this.entries[j][i];
            }
        }
        return res;
    }

    public Matrix add(Matrix m) throws IllegalArgumentException {
        if (Helpers.hasIncorrectDims(this, m))
            throw new IllegalArgumentException("Cannot add matrices of different dimensions!");
        Matrix res = new Matrix(this.rows, this.cols);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                res.entries[i][j] = this.entries[i][j] + m.entries[i][j];
            }
        }
        return res;
    }

    public Matrix subtract(Matrix m) throws IllegalArgumentException {
        if (Helpers.hasIncorrectDims(this, m))
            throw new IllegalArgumentException("Cannot subtract matrices of different dimensions!");
        Matrix res = new Matrix(this.rows, this.cols);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                res.entries[i][j] = this.entries[i][j] - m.entries[i][j];
            }
        }
        return res;
    }

    public Matrix multiplyByScalar(double a) throws IllegalArgumentException {
        Matrix res = new Matrix(this.rows, this.cols);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                res.entries[i][j] = this.entries[i][j] * a;
            }
        }
        return res;
    }

    public Matrix multiplyElementWise(Matrix m) throws IllegalArgumentException {
        if (Helpers.hasIncorrectDims(this, m))
            throw new IllegalArgumentException("Cannot multiply (element-wise) matrices of different dimensions!");
        Matrix res = new Matrix(this.rows, this.cols);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.cols; j++) {
                res.entries[i][j] = this.entries[i][j] * m.entries[i][j];
            }
        }
        return res;
    }

    public Matrix multiply(Matrix m) throws IllegalArgumentException {
        if (this.cols != m.rows)
            throw new IllegalArgumentException("Cannot multiply matrices of non-compatible dimensions!");
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

    static void main() {

        double[][] a = {{1, 2}, {3, 4}, {5, 6}};
        Matrix m1 = new Matrix(a); // 3 x 2

        double[][] b = {{5, 6}, {7, 8}, {9, 10}};
        Matrix m2 = new Matrix(b); // 3 x 2

        double[][] c = {{1, 2, 3}, {4, 5, 6}};
        Matrix m3 = new Matrix(c); // 2 x 3

        double[][] d = {{1}, {2}, {3}};
        Matrix m4 = new Matrix(d); // 3 x 1

        // TEST: matrix transpose
        m1.transpose().printMatrix();
        System.out.println();

        // TEST: matrix addition
        try {
            m1.add(m2).printMatrix();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // TEST: matrix multiplication
        try {
            m3.multiply(m1).printMatrix();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // TEST: matrix-vector multiplication
        try {
            m3.multiply(m4).printMatrix();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // TEST: other methods
        Matrix m5 = new Matrix(3, 3);
        m5.fill(3);
        m5.printMatrix();
        System.out.println();
        System.out.println(m5.getNumRows());
        System.out.println(m5.getNumCols());
        System.out.println();
        m5.setEntry(2, 1, 3.14);
        System.out.println(m5.getEntry(2, 1));
    }
}
