package org.rsultan.bandit.algorithms

import kotlin.math.exp
import kotlin.math.ln

class AnnealedSoftmax(nbArms: Int) : AbstractSoftmaxAlgorithm(nbArms) {

    override fun selectArm(): Int {
        val time = counts.sum().toFloat() + 1.0f
        val temperature = 1.0f / ln(time + 0.0000001f)
        val sum = values.map { exp(it / temperature) }.sum()
        val probabilities = values.map { exp(it / temperature) / sum }
        return categoricalDraw(probabilities)
    }
}

class AnnealedSoftmaxAlgorithmBuilder : AlgorithmBuilder<AnnealedSoftmax> {

    private var nbArms: Int = 2

    fun setArms(nbArms: Int): AnnealedSoftmaxAlgorithmBuilder {
        this.nbArms = nbArms
        return this
    }

    override fun build(): AnnealedSoftmax = AnnealedSoftmax(nbArms)

}