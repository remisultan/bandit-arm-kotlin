package org.rsultan.bandit.algorithms


import java.security.SecureRandom

class EpsilonGreedy(private val epsilon: Float, nbArms: Int) : BanditAlgorithm {

    private val random = SecureRandom()
    private val counts = (1..nbArms).map { 0 }.toTypedArray()
    private val values = (1..nbArms).map { 0.0f }.toTypedArray()

    init {
        if (epsilon > 1.0 || epsilon < 0.0) throw IllegalArgumentException("epsilon must be between 0.0 and 1.0")
        if (nbArms < 2) throw IllegalArgumentException("Must have a least one arm.")
    }

    private fun maxIndex() = values.indexOf(values.max())
    override fun selectArm() = if (random.nextFloat() > epsilon) maxIndex() else random.nextInt(counts.size)
    override fun update(chosenArm: Int, reward: Float) {
        val armCount = ++counts[chosenArm]
        val armValue = values[chosenArm]
        values[chosenArm] = ((armCount - 1) / armCount.toFloat()) * armValue + (1 / armCount.toFloat()) * reward
    }
}

class EpsilonGreedyBuilder() : AlgorithmBuilder<EpsilonGreedy> {

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
