package neuralNetwork;

import dataStructures.Matrix;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final double learningRate = 0.1;
    private List<Layer> layers;

    public NeuralNetwork() {
        this.layers = new ArrayList<>();
        this.layers.add(new HiddenLayer(256, 128));
        this.layers.add(new HiddenLayer(128, 64));
        this.layers.add(new OutputLayer(64, 1));
    }

    private Matrix forwardProp(Matrix input) {
        Matrix current = input;
        for (Layer layer : this.layers)
            current = layer.forwardPass(current);
        return current;
    }

    public void train() {
        // TODO
    }
}