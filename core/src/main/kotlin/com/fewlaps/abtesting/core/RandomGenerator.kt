package com.fewlaps.abtesting.core

open class RandomGenerator {

    open fun getRandom(bound : Int) = java.util.Random().nextInt(bound)
}