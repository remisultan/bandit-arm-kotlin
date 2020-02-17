package org.rsultan.bandit.runner

import org.rsultan.bandit.algorithms.AlgorithmBuilder
import org.rsultan.bandit.algorithms.BanditAlgorithm
import org.rsultan.bandit.arms.BanditArm
import java.io.File
import java.util.*

class BanditRunner<ARM : BanditArm>(
    private val algorithmBuilder: AlgorithmBuilder<out BanditAlgorithm>,
    private val arms: Array<ARM>,
    private val nbSimulations: Int,
    private val horizon: Int,
    private val fileName: String = "/tmp/" + UUID.randomUUID() + ".tsv"
) : Runnable {

    private val cumulativeRewards = (1..(nbSimulations * horizon)).map { 0.0f }.toTypedArray()

    override fun run() {
        val writer = File(fileName).printWriter()
        (0 until nbSimulations).forEach { sim ->
            val algorithm = algorithmBuilder.build()
            (0 until horizon).forEach { t ->
                val index = sim * horizon + t
                val chosenArm = algorithm.selectArm()
                val reward = arms[chosenArm].draw()
                val cumulativeReward = if (t == 0) reward else cumulativeRewards[index - 1] + reward
                cumulativeRewards[index] = cumulativeReward
                algorithm.update(chosenArm, reward)
                writer.write("${sim + 1}\t${t + 1}\t$chosenArm\t$reward\t$cumulativeReward\n")
            }
        }
        writer.close()
    }
}