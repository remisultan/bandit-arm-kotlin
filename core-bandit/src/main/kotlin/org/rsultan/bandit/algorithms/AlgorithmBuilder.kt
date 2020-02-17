package org.rsultan.bandit.algorithms

interface AlgorithmBuilder<ALG : BanditAlgorithm> {

    fun build(): ALG
}