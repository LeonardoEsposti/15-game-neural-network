package neuralNetwork;

public class InputLayer extends Layer {

    public InputLayer(double[] neurons, double[][] weights, double[] biases) {
        super(neurons, weights, biases);
    }

    public void forwardPass() {
        computeValues();
    }
}
