package neuralNetwork;

import dataStructures.Matrix;

import java.util.Random;


public abstract class Layer implements Helpers {

    //for testing
    private static int total = 0;
    private int layerN = 0;

    public int getN() {
        return layerN;
    }


    protected Matrix values;  // n x 1 matrix
    protected Matrix weights;  // k x n matrix
    private Matrix biases;  // k x 1 matrix
    protected Matrix input;


    protected abstract Matrix forwardPass(Matrix input);

    protected Layer(int inputSize, int outputSize) {
        this.layerN = total;
        total++;

        this.values = new Matrix(inputSize, 1);
        this.weights = new Matrix(outputSize, inputSize);
        this.biases = new Matrix(outputSize, 1);
        this.initWeights();
        this.biases.fill(0);

    }

    protected Layer(int inputSize, int outputSize, Matrix savedWeights, Matrix savedBiases) {
        this.layerN = total;
        total++;
        this.values = new Matrix(inputSize, 1);
        this.weights = savedWeights;
        this.biases = savedBiases;
    }

    private void initWeights() {
        int n = this.weights.getNumCols();
        double scale = Math.sqrt(2.0 / n);
        for (int i = 0; i < this.weights.getNumRows(); i++) {
            for (int j = 0; j < n; j++) {
                this.weights.setEntry(i, j, new Random().nextGaussian() * scale);  // He initialization
            }
        }
    }

    protected void computeValues() {
        this.values = this.weights.multiply(this.input).add(this.biases);  // Z = W * X + B
    }

    protected Matrix backwardPass(Matrix error, double learningRate) {
        Matrix gradient = error.multiplyElementWise(Helpers.leakyReluDerivative(this.values));  // dZ = d(cost) * d(relu(Z))
        Matrix prevError = this.weights.transpose().multiply(gradient);  // d(cost)_prev = W^T * dZ
        this.updateParams(gradient, learningRate);
        return prevError;
    }

    private void updateParams(Matrix gradient, double learningRate) {
        Matrix weightGradient = gradient.multiply(this.input.transpose());  // dW = dZ * X^T
        this.weights = this.weights.subtract(weightGradient.multiplyByScalar(learningRate));  // W = W - (dW * learningRate)
        this.biases = this.biases.subtract(gradient.multiplyByScalar(learningRate));  // B = B - (dZ * learningRate)
    }

    private StringBuilder matrixToString(Matrix matrix) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < matrix.getNumRows(); i++) {
            for (int j = 0; j < matrix.getNumCols(); j++) {
                newString.append(",").append(matrix.getEntry(i, j));
            }
        }
        return newString;
    }

    public String weigthsToString() {
        StringBuilder weightString= new StringBuilder().append(this.weights.getNumRows()).append( "," ).append(this.weights.getNumCols());
        return  weightString.append(matrixToString(this.weights)).toString();
    }
    public String biasesToString() {
        StringBuilder biasesString= new StringBuilder().append(this.weights.getNumRows()).append( ",0" );
        return  biasesString.append(matrixToString(this.biases)).toString();
    }

}


