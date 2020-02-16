package org.rsultan.bandit

import org.rsultan.bandit.algorithms.*
import org.rsultan.bandit.arms.BernouilliArm
import org.rsultan.bandit.runner.BanditRunner

fun main(args: Array<String>) {
    // epsilonGreedyExample()
    // annealedEpsilonGreedy()
    // softmaxExample()
    annealedSoftmaxExample()
}

private fun epsilonGreedyExample() {
    val arms = listOf(0.1f, 0.1f, 0.1f, 0.9f).shuffled().toTypedArray()
    //val arms = listOf(0.3f, 0.9f).shuffled().toTypedArray()
    //val arms = (1..2000).map { SecureRandom().nextFloat() }.shuffled().toTypedArray()
    //val arms = (1..20).map { 0.1f }.shuffled().toTypedArray()
    //val arms = (1..20).map { 0.1f }.shuffled().toTypedArray()
    val bernouilliArms = arms.map { BernouilliArm(it) }.toTypedArray()

    println("Best arm is : " + arms.indexOf(arms.max()))

    val epsilsons = arrayOf(0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f)
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
    BanditRunner(algo, bernouilliArms, 5000, 250, "/tmp/epsilson-annealed.tsv").run()
}

private fun softmaxExample() {
    val arms = listOf(0.1f, 0.1f, 0.1f, 0.9f).shuffled().toTypedArray()
    val bernouilliArms = arms.map { BernouilliArm(it) }.toTypedArray()
    val temperatures = arrayOf(0.1f, 0.2f, 0.3f, 0.4f, 0.5f)

    println("Best arm is : " + arms.indexOf(arms.max()))
    temperatures.forEach { tps ->
        val algo = SoftmaxAlgorithmBuilder().setTemperature(tps).setArms(arms.size)
        BanditRunner(algo, bernouilliArms, 5000, 250, "/tmp/softmax-$tps.tsv").run()
    }
}

private fun annealedSoftmaxExample() {
    val arms = listOf(0.1f, 0.1f, 0.1f, 0.9f).shuffled().toTypedArray()
    val bernouilliArms = arms.map { BernouilliArm(it) }.toTypedArray()

    println("Best arm is : " + arms.indexOf(arms.max()))
    val algo = AnnealedSoftmaxAlgorithmBuilder().setArms(arms.size)
    BanditRunner(algo, bernouilliArms, 5000, 250, "/tmp/softmax-annealed.tsv").run()
}