package org.rsultan.bandit.algorithms

import kotlin.math.ln
import kotlin.math.sqrt

class UCBAlgorithm(nbArms: Int) : AbstractBanditAlgorithm(nbArms) {

    override fun selectArm(): Int {
        val zeroCountArm = counts.indexOfFirst { it == 0 }
        if (zeroCountArm != -1) {
            return zeroCountArm
        }
        val totalCounts = counts.sum().toFloat()
        val ucbValues = counts.indices.map {
            val bonus = sqrt(2 * ln(totalCounts)) / counts[it].toFloat()
            values[it] + bonus
        }
        return ucbValues.indexOf(ucbValues.max())
    }
}

class UCBAlgorithmBuilder : AlgorithmBuilder<UCBAlgorithm> {

    private var nbArms: Int = 2

    fun setArms(nbArms: Int): UCBAlgorithmBuilder {
        this.nbArms = nbArms
        return this
    }

    override fun build(): UCBAlgorithm = UCBAlgorithm(nbArms)

}