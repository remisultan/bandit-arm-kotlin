package org.rsultan.bandit.algorithms

import java.security.SecureRandom
import kotlin.math.ln
import kotlin.math.log10

class AnnealedEpsilonGreedy(nbArms:Int) : BanditAlgorithm {

    private val random = SecureRandom()
    private val counts = (1..nbArms).map { 0 }.toTypedArray()
    private val values = (1..nbArms).map { 0.0f }.toTypedArray()

    override fun maxIndex() = values.indexOf(values.max())

    override fun selectArm(): Int {
        val epsilon = 1.0f / (ln(counts.sum() + 0.0f))
        return if (random.nextFloat() > epsilon) maxIndex() else random.nextInt(counts.size)
    }

    override fun update(chosenArm: Int, reward: Float) {
        val armCount = ++counts[chosenArm]
        val armValue = values[chosenArm]
        values[chosenArm] = ((armCount - 1) / armCount.toFloat()) * armValue + (1 / armCount.toFloat()) * reward
    }
}

class AnnealedEpsilonGreedyBuilder() : AlgorithmBuilder<AnnealedEpsilonGreedy> {

    private var nbArms: Int = 2

    fun setArms(nbArms:Int): AnnealedEpsilonGreedyBuilder {
        this.nbArms = nbArms
        return this
    }

    override fun build(): AnnealedEpsilonGreedy = AnnealedEpsilonGreedy(nbArms)
}