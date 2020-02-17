package org.rsultan.bandit.algorithms

import kotlin.math.ln

class AnnealedEpsilonGreedy(nbArms:Int) : AbstractBanditAlgorithm(nbArms) {

    private fun maxIndex() = values.indexOf(values.max())

    override fun selectArm(): Int {
        val epsilon = 1.0f / (ln(counts.sum() + 0.0f))
        return if (random.nextFloat() > epsilon) maxIndex() else random.nextInt(counts.size)
    }
}

class AnnealedEpsilonGreedyBuilder : AlgorithmBuilder<AnnealedEpsilonGreedy> {

    private var nbArms: Int = 2

    fun setArms(nbArms:Int): AnnealedEpsilonGreedyBuilder {
        this.nbArms = nbArms
        return this
    }

    override fun build(): AnnealedEpsilonGreedy = AnnealedEpsilonGreedy(nbArms)
}