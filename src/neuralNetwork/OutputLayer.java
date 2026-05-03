package neuralNetwork;
import utils.Activations;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class OutputLayer  extends Layer  {

    public void softmax(){
        for (int i = 0; i < this.PreAct.getNumElements() ; i++ ){
            AftAct.set(i, softmaxSingle(this.PreAct.get(i), this.PreAct));
        }
    }

    public void ForwardPass() {
        ForwardPassGeneric();
        softmax();
    }
}

