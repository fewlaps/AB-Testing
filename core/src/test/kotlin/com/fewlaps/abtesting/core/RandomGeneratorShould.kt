package com.fewlaps.abtesting.core

import junit.framework.Assert.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class RandomGeneratorShould {

    lateinit var randomGenerator: RandomGenerator

    @Before
    fun setUp() {
        randomGenerator = RandomGenerator()
    }

    @Test
    fun neverReturnMaxValue() {
        val maxValue = 5;
        for (i in 0..10000000) {
            val randomValue = randomGenerator.getRandom(maxValue);
            assertThat(randomValue).isNotEqualTo(maxValue)
        }
    }

    @Test
    fun returnsTheMinValue() {
        val maxValue = 5;
        var minValueHasBeenReturned = false;
        for (i in 0..10000000) {
            val randomValue = randomGenerator.getRandom(maxValue)
            if (randomValue == 0) {
                minValueHasBeenReturned = true;
            }
        }
        assertTrue(minValueHasBeenReturned)
    }
}