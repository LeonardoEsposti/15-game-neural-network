package utils;

public interface Activations {

    default double relu(double value) {
        return Math.max(0, value);
    }

    default double reluDerivative(double value) {
        if (value > 0)
            return 1;
        return 0;
    }

    default double softmaxSingle(double value, double sum) {
        return Math.exp(value) / sum;
        // FEATURE: we may add numerical stability for avoiding exponential overflow (not strictly necessary)
    }
}