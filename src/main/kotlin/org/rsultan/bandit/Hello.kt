package org.rsultan.bandit

import org.rsultan.bandit.algorithms.AnnealedEpsilonGreedy
import org.rsultan.bandit.algorithms.AnnealedEpsilonGreedyBuilder
import org.rsultan.bandit.algorithms.EpsilonGreedyBuilder
import org.rsultan.bandit.arms.BernouilliArm
import org.rsultan.bandit.runner.BanditRunner

fun main(args: Array<String>) {
    //epsilonGreedyExample()
    annealedEpsilonGreedy()
}

private fun epsilonGreedyExample() {
    val arms = listOf(0.1f, 0.1f, 0.1f, 0.9f).shuffled().toTypedArray()
    //val arms = listOf(0.3f, 0.9f).shuffled().toTypedArray()
    //val arms = (1..2000).map { SecureRandom().nextFloat() }.shuffled().toTypedArray()
    //val arms = (1..20).map { 0.1f }.shuffled().toTypedArray()
    //val arms = (1..20).map { 0.1f }.shuffled().toTypedArray()
    val bernouilliArms = arms.map { BernouilliArm(it) }.toTypedArray()

    println("Best arm is : " + arms.indexOf(arms.max()))

    val epsilsons = arrayOf(0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f)
    epsilsons.forEach { eps ->
        val algo = EpsilonGreedyBuilder().setEpsilon(eps).setArms(arms.size)
        BanditRunner(algo, bernouilliArms, 5000, 250, "/tmp/epsilson-$eps.tsv").run()
    }
}

private fun annealedEpsilonGreedy() {
    val arms = listOf(0.1f, 0.1f, 0.1f, 0.9f).shuffled().toTypedArray()
    val bernouilliArms = arms.map { BernouilliArm(it) }.toTypedArray()

    println("Best arm is : " + arms.indexOf(arms.max()))
    val algo = AnnealedEpsilonGreedyBuilder().setArms(arms.size)
    BanditRunner(algo, bernouilliArms, 5000, 2500, "/tmp/epsilson-annealed.tsv").run()
}
