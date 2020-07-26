package de.longuyen.neuronalnetwork

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.ops.transforms.Transforms

interface LossFunction {
    fun forward(yTrue: INDArray, yPrediction: INDArray) : INDArray

    fun backward(yTrue: INDArray, yPrediction: INDArray) : INDArray
}

class MAE : LossFunction {
    override fun forward(yTrue: INDArray, yPrediction: INDArray): INDArray {
        val diff = yPrediction.sub(yTrue)
        val absolute = Transforms.abs(diff)
        return absolute.sum(true, 0).mean()
    }

    override fun backward(yTrue: INDArray, yPrediction: INDArray): INDArray {
        val aOutput: Array<DoubleArray> = yPrediction.toDoubleMatrix()
        val aTarget: Array<DoubleArray> = yTrue.toDoubleMatrix()
        val gradients: Array<DoubleArray> = yTrue.toDoubleMatrix()
        for (y in aOutput.indices) {
            for (x in aOutput[y].indices) {
                if (aOutput[y][x] > aTarget[y][x]) {
                    gradients[y][x] = 1.0
                } else {
                    gradients[y][x] = -1.0
                }
            }
        }
        return Nd4j.createFromArray(gradients).castTo(yTrue.dataType())
    }
}