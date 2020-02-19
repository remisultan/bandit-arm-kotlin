package org.rsultan.bandit.algorithms

import kotlin.math.exp

class Softmax(private val temperature: Float, nbArms: Int) : AbstractSoftmaxAlgorithm(nbArms) {

    override fun selectArm(): Int {
        val sum = values.map { exp(it / temperature) }.sum()
        val probabilities = values.map { exp(it / temperature) / sum }
        return categoricalDraw(probabilities)
    }
}

class SoftmaxBuilder : AlgorithmBuilder<Softmax> {

    private var nbArms: Int = 2
    private var temperature: Float = 0.0f

    fun setArms(nbArms: Int): SoftmaxBuilder {
        this.nbArms = nbArms
        return this
    }

    fun setTemperature(temperature: Float): SoftmaxBuilder {
        this.temperature = temperature
        return this
    }

    override fun build(): Softmax = Softmax(temperature, nbArms)

}