package org.rsultan.bandit

import org.rsultan.bandit.algorithms.*
import org.rsultan.bandit.arms.BernouilliArm
import org.rsultan.bandit.runner.BanditRunner
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.float
import com.github.ajalt.clikt.parameters.types.int
import java.lang.IllegalArgumentException

class Bandit : CliktCommand() {

    companion object {
        const val UCB_ALG = "UCB"
        const val EPSILON_ALG = "EpsilonGreedy"
        const val SOFTMAX_ALG = "Softmax"
    }

    private val simulations by option(help = "The Number of simulation you want to run").int().default(5000)
    private val trials by option(help = "The number of trials for a simulation").int().default(250)
    private val arms by option(help = "The Bernoulli probabilities of your arms (multiple values separated by \",\")").float().split(",").required()
    private val algorithm by option(help = "Algorithm Type. Values : $EPSILON_ALG, $SOFTMAX_ALG, $UCB_ALG").required()
    private val temperature by option("-t", "-e", help = "Threshold of non-annealed algorithms").float().default(0.5f)
    private val annealed by option(help = "Annealed version of algorithm. (only for $EPSILON_ALG, $SOFTMAX_ALG). Cancels temperature").flag()
    private val fileName by option(help = "The output file name").required()

    override fun run() {
        val shuffledArms = this.arms.shuffled()
        val arms = shuffledArms.map { BernouilliArm(it) }.toTypedArray()

        val algorithm: AlgorithmBuilder<out BanditAlgorithm> = when (this.algorithm.toLowerCase()) {
            EPSILON_ALG.toLowerCase() -> getEpsilonAlgorithm()
            SOFTMAX_ALG.toLowerCase() -> getSoftmaxAlgorithm()
            UCB_ALG.toLowerCase() -> UCBBuilder().setArms(this.arms.size)
            else -> throw IllegalArgumentException("$algorithm not supported")
        }

        println("Best arm is : " + shuffledArms.indexOf(shuffledArms.max()))
        BanditRunner(algorithm, arms, simulations, trials, fileName).run()
    }

    private fun getSoftmaxAlgorithm() =
        if (annealed)
            AnnealedSoftmaxAlgorithmBuilder().setArms(this.arms.size)
        else
            SoftmaxBuilder().setArms(this.arms.size).setTemperature(temperature)

    private fun getEpsilonAlgorithm() =
        if (annealed)
            AnnealedEpsilonGreedyBuilder().setArms(this.arms.size)
        else
            EpsilonGreedyBuilder().setArms(this.arms.size).setEpsilon(temperature)
}

fun main(args: Array<String>) = Bandit().main(args)