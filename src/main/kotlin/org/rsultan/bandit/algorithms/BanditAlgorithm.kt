package org.rsultan.bandit.algorithms

interface BanditAlgorithm {

    fun maxIndex(): Int

    fun selectArm(): Int

    fun update(chosenArm: Int, reward: Float)
}