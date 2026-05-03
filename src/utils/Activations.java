package utils;

public interface Activations {

    default double relu(double value) {
        return Math.max(0, value);
    }

    default double relu_derivative(double value) {
        if (value > 0)
            return 1;
        return 0;
    }

    default double softmax(double value, double[] values) {
        double sum = 0;
        for (double v : values)
            sum += Math.exp(v);
        return Math.exp(value) / sum;

        // FEATURE: we may add numerical stability for avoiding exponential overflow (not strictly necessary)
    }
}
