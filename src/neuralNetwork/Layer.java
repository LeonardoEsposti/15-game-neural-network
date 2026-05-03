package neuralNetwork;


import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;



import utils.Activations;

public abstract class Layer implements Activations {

    DMatrixRMaj input; //just the syntax for the creating the matrix/vector
    DMatrixRMaj PreAct;
    DMatrixRMaj AftAct;
    DMatrixRMaj weights;
    DMatrixRMaj biases;


    public void ForwardPassGeneric() { // new W = w*inp + bias
        CommonOps_DDRM.mult(this.input,this.weights , PreAct);
        CommonOps_DDRM.addEquals(PreAct, biases);
    }
}


