package org.rsultan.bandit.algorithms

import java.security.SecureRandom
import kotlin.math.exp

class SoftmaxAlgorithm(nbArms: Int, private val temperature: Float) : BanditAlgorithm {

    private val random = SecureRandom()
    private val counts = (1..nbArms).map { 0 }.toTypedArray()
    private val values = (1..nbArms).map { 0.0f }.toTypedArray()

    override fun selectArm(): Int {
        val sum = values.map { exp(it / temperature) }.sum()
        val probabilities = values.map { exp(it / temperature) / sum }
        return categoricalDraw(probabilities)
    }

    override fun update(chosenArm: Int, reward: Float) {
        val armCount = ++counts[chosenArm]
        val armValue = values[chosenArm]
        values[chosenArm] = ((armCount - 1) / armCount.toFloat()) * armValue + (1 / armCount.toFloat()) * reward
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

    fun setArms(nbArms:Int): SoftmaxAlgorithmBuilder {
        this.nbArms = nbArms
        return this
    }

    fun setTemperature(temperature:Float): SoftmaxAlgorithmBuilder {
        this.temperature = temperature
        return this
    }

    override fun build(): SoftmaxAlgorithm = SoftmaxAlgorithm(nbArms, temperature)

}