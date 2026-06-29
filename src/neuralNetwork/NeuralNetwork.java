package neuralNetwork;

import dataStructures.Matrix;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Helpers {

    private final double learningRate = 0.1;
    private final List<Layer> layers;

    public NeuralNetwork() {
        this.layers = new ArrayList<>();
        this.layers.add(new HiddenLayer(256, 128));
        this.layers.add(new HiddenLayer(128, 64));
        this.layers.add(new OutputLayer(64, 1));
    }

    private Matrix predict(Matrix input) {
        Matrix values = input;
        for (Layer layer : this.layers)
            values = layer.forwardPass(values);
        return values;
    }

    public void train(Matrix input, Matrix target) {
        Matrix prediction = this.predict(input);
        Matrix error = prediction.subtract(target);
        for (int i = this.layers.size() - 1; i >= 0; i--) {
            Layer layer = this.layers.get(i);
            error = layer.backwardPass(error, this.learningRate);
        }
    }
}