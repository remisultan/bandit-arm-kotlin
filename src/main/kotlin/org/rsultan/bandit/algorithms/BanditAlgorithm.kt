package org.rsultan.bandit.algorithms

interface BanditAlgorithm {

    fun selectArm(): Int

    fun update(chosenArm: Int, reward: Float)
}
