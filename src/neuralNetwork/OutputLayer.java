package neuralNetwork;

public class OutputLayer extends Layer {

    public OutputLayer(double[] neurons, double[][] weights, double[] biases) {
        super(neurons, weights, biases);
    }

    public void softmax() {
        // computes the sum and runs softmaxSingle for each neuron
    }

    public void forwardPass() {
        computeValues();
        softmax();
    }
}

