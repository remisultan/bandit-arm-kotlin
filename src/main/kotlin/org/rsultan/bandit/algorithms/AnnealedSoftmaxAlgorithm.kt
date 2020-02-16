package org.rsultan.bandit.algorithms

import kotlin.math.exp
import kotlin.math.ln

class AnnealedSoftmaxAlgorithm(nbArms: Int) : AbstractBanditAlgorithm(nbArms) {

    override fun selectArm(): Int {
        val time = counts.sum().toFloat() + 1.0f
        val temperature = 1.0f / ln(time + 0.0000001f)
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

class AnnealedSoftmaxAlgorithmBuilder : AlgorithmBuilder<AnnealedSoftmaxAlgorithm> {

    private var nbArms: Int = 2

    fun setArms(nbArms: Int): AnnealedSoftmaxAlgorithmBuilder {
        this.nbArms = nbArms
        return this
    }

    override fun build(): AnnealedSoftmaxAlgorithm = AnnealedSoftmaxAlgorithm(nbArms)

}