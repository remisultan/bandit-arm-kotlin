package org.rsultan.bandit.algorithms

import kotlin.math.exp

class SoftmaxAlgorithm(private val temperature: Float, nbArms: Int) : AbstractBanditAlgorithm(nbArms) {

    override fun selectArm(): Int {
        val sum = values.map { exp(it / temperature) }.sum()
        val probabilities = values.map { exp(it / temperature) / sum }
        return categoricalDraw(probabilities)
    }

    private fun categoricalDraw(probabilities: List<Float>): Int {
        val rand = random.nextFloat()
        var cumulativeProbability = 0.0f
        val mapIndexed = probabilities.filter {
            cumulativeProbability += it
            cumulativeProbability > rand
        }.mapIndexed { idx, _ -> idx }

        return if (mapIndexed.isEmpty()) (probabilities.size - 1) else mapIndexed.first()
    }
}

class SoftmaxAlgorithmBuilder : AlgorithmBuilder<SoftmaxAlgorithm> {

    private var nbArms: Int = 2
    private var temperature: Float = 0.0f

    fun setArms(nbArms: Int): SoftmaxAlgorithmBuilder {
        this.nbArms = nbArms
        return this
    }

    fun setTemperature(temperature: Float): SoftmaxAlgorithmBuilder {
        this.temperature = temperature
        return this
    }

    override fun build(): SoftmaxAlgorithm = SoftmaxAlgorithm(temperature, nbArms)

}