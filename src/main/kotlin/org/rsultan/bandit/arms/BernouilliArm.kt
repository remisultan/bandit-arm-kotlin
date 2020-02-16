package org.rsultan.bandit.arms

import java.security.SecureRandom

class BernouilliArm(private val probability: Float) : BanditArm {

    private val random = SecureRandom()

    init {
        if (probability > 1.0 || probability < 0.0) throw IllegalArgumentException("probability should be between 0.0 and 1.0")
    }

    override fun draw(): Float = if (random.nextFloat() > probability) 0.0f else 1.0f

}