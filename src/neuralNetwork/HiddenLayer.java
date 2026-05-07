package neuralNetwork;

public class HiddenLayer extends Layer {

    public HiddenLayer(double[] neurons, double[][] weights, double[] biases) {
        super(neurons, weights, biases);
    }

    public void forwardPass() {
        computeValues();
    }
}
