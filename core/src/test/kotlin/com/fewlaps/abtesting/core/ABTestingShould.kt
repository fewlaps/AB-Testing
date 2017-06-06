package com.fewlaps.abtesting.core

import com.fewlaps.abtesting.core.doubles.ABTestingRepositoryEmpty
import com.fewlaps.abtesting.core.doubles.ABTestingRepositoryFake
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

val EXPERIMENT_NAME = "yes or no"
val OPTION_YES = "yes"
val OPTION_NO = "no"
val YES_OR_NO_EXPERIMENT = Experiment(EXPERIMENT_NAME, listOf(OPTION_YES, OPTION_NO))

class ABTestingShould {

    lateinit var randomGenerator: RandomGenerator
    lateinit var abTesting: ABTesting

    @Before
    fun setUp() {
        randomGenerator = mock(RandomGenerator::class.java)
        abTesting = ABTesting(listOf(YES_OR_NO_EXPERIMENT), ABTestingRepositoryFake(), randomGenerator)
    }

    @Test
    fun returnListOfExperiments() {
        val experiments: List<Experiment> = abTesting.experiments

        assertEquals(EXPERIMENT_NAME, experiments[0].name)
    }

    @Test
    fun returnListOfExperimentsWithCurrentOptions() {
        val currentOptions: List<CurrentOption> = abTesting.getCurrentOptions()
        given(randomGenerator.getRandom(anyInt())).willReturn(0)

        assertEquals(EXPERIMENT_NAME, currentOptions[0].experiment)
        assertEquals(OPTION_YES, currentOptions[0].option)
    }

    @Test
    fun returnDetailOfExperiment() {
        val storedExperiment = abTesting.getExperiment(EXPERIMENT_NAME)

        assertEquals(EXPERIMENT_NAME, storedExperiment.name)
        assertEquals(OPTION_YES, storedExperiment.options[0])
        assertEquals(OPTION_NO, storedExperiment.options[1])
    }

    @Test(expected = ExperimentNotFoundException::class)
    fun failWhenExperimentDoesntExists() {
        abTesting.getExperiment("unexisting test")
    }

    @Test
    fun manageMultipleOptionsWithSameChance() {
        val experimentName = "three options experiment"
        val experiment = Experiment(experimentName, listOf("a", "b", "c"))

        abTesting = ABTesting(listOf(experiment), ABTestingRepositoryEmpty(), randomGenerator)
        assertRandomValueGivesOption(experimentName, 0, "a")
        assertRandomValueGivesOption(experimentName, 1, "b")
        assertRandomValueGivesOption(experimentName, 2, "c")
    }

    @Test
    fun returnedOptionShouldBeAlwaysTheSame() {
        mockRandomToReturn(0)
        val currentOption = abTesting.getCurrentOptionFor(EXPERIMENT_NAME)
        mockRandomToReturn(1)

        val newOption = abTesting.getCurrentOptionFor(EXPERIMENT_NAME)

        assertEquals(currentOption, newOption)
    }

    @Test
    fun giveRandomFirstOption() {
        abTesting = ABTesting(listOf(YES_OR_NO_EXPERIMENT), ABTestingRepositoryEmpty(), RandomGenerator())
        var yesCount = 0;
        var noCount = 0;

        for (i in 1..100) {
            when (abTesting.getCurrentOptionFor(EXPERIMENT_NAME)) {
                OPTION_YES -> yesCount++
                OPTION_NO -> noCount++
            }
        }

        System.out.print("Yes: ${yesCount}\n")
        System.out.print("No: ${noCount}\n")

        assertThat(yesCount).isBetween(30, 70)
        assertThat(noCount).isBetween(30, 70)
    }

    private fun assertRandomValueGivesOption(experimentName: String, random: Int, expectedOption: String) {
        given(randomGenerator.getRandom(anyInt())).willReturn(random)
        val resultOption = abTesting.getCurrentOptionFor(experimentName)
        assertEquals(expectedOption, resultOption)
    }

    private fun mockRandomToReturn(value: Int) {
        given(randomGenerator.getRandom(anyInt())).willReturn(value)
    }
}