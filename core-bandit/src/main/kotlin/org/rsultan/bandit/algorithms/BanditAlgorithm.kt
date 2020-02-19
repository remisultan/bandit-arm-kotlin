package org.rsultan.bandit.algorithms

import java.security.SecureRandom

interface BanditAlgorithm {

    fun selectArm(): Int

    fun update(chosenArm: Int, reward: Float)
}

abstract class AbstractBanditAlgorithm(nbArms: Int) : BanditAlgorithm {

    protected val random = SecureRandom()
    protected val counts = (1..nbArms).map { 0 }.toTypedArray()
    protected val values = (1..nbArms).map { 0.0f }.toTypedArray()

    override fun update(chosenArm: Int, reward: Float) {
        val armCount = ++counts[chosenArm]
        val armValue = values[chosenArm]
        values[chosenArm] = ((armCount - 1) / armCount.toFloat()) * armValue + (1 / armCount.toFloat()) * reward
    }
}

abstract class AbstractSoftmaxAlgorithm(nbArms: Int) : AbstractBanditAlgorithm(nbArms) {

    fun categoricalDraw(probabilities: List<Float>): Int {
        val rand = random.nextFloat()
        var cumulativeProbability = 0.0f
        for (i in probabilities.indices) {
            cumulativeProbability += probabilities[i]
            if (cumulativeProbability > rand) {
                return i
            }
        }
        return probabilities.lastIndex
    }
}

