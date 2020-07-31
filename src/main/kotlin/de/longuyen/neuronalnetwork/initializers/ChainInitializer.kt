package de.longuyen.neuronalnetwork.initializers

import org.nd4j.linalg.api.buffer.DataType
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import java.io.Serializable
import kotlin.math.sqrt

class ChainInitializer  : Initializer(), Serializable {
    override fun initialize(layers: IntArray): MutableMap<String, INDArray> {
        val weights = mutableMapOf<String, INDArray>()
        for (i in 1 until layers.size) {
            // i - 1 rows, i columns
            // Initialize weights
            weights["W$i"] = (Nd4j.rand(*intArrayOf(layers[i], layers[i - 1])).mul(sqrt(2.0 / layers[i - 1].toDouble()))).castTo(DataType.DOUBLE)
            // Initialize biases
            weights["b$i"] = Nd4j.zeros(*intArrayOf(layers[i], 1)).castTo(DataType.DOUBLE)
        }
        return weights
    }
}