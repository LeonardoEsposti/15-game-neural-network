package utils;
import org.ejml.data.DMatrixRMaj;
public interface Activations {

    default double relu(double value) {
        return Math.max(0, value);
    }

    default double relu_derivative(double value) {
        if (value > 0)
            return 1;
        return 0;
    }

    default double softmaxSingle(double value, DMatrixRMaj values) {
        double sum = 0;
        for (int i = 0; i < values.getNumElements(); i++)
            sum += Math.exp(values.get(i));
        return Math.exp(value) / sum;

        // FEATURE: we may add numerical stability for avoiding exponential overflow (not strictly necessary)
    }
}


