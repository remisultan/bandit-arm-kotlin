package org.rsultan.bandit.algorithms

class EpsilonGreedy(private val epsilon: Float, nbArms: Int) : AbstractBanditAlgorithm(nbArms) {

    init {
        if (epsilon > 1.0 || epsilon < 0.0) throw IllegalArgumentException("epsilon must be between 0.0 and 1.0")
        if (nbArms < 2) throw IllegalArgumentException("Must have a least one arm.")
    }

    private fun maxIndex() = values.indexOf(values.max())
    override fun selectArm() = if (random.nextFloat() > epsilon) maxIndex() else random.nextInt(counts.size)
}

class EpsilonGreedyBuilder : AlgorithmBuilder<EpsilonGreedy> {

    private var epsilon: Float = 0.0f
    private var arms: Int = 2

    fun setEpsilon(epsilon: Float): EpsilonGreedyBuilder {
        this.epsilon = epsilon
        return this
    }
    fun setArms(arms: Int): EpsilonGreedyBuilder {
        this.arms = arms
        return this
    }

    override fun build(): EpsilonGreedy = EpsilonGreedy(epsilon, arms)

}
