package com.fewlaps.abtesting.core

import com.fewlaps.abtesting.core.doubles.ABTestingRepositoryEmpty
import com.fewlaps.abtesting.core.doubles.ABTestingRepositoryFake
import com.fewlaps.abtesting.core.repeat.Repeat
import com.fewlaps.abtesting.core.repeat.RepeatRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

val OPTION_YES = "yes"
val OPTION_NO = "no"
val YES_OR_NO_EXPERIMENT_NAME = "yes or no"
val YES_OR_NO_EXPERIMENT = Experiment(YES_OR_NO_EXPERIMENT_NAME, listOf(OPTION_YES, OPTION_NO))

val OPTION_TRUE = "true"
val OPTION_FALSE = "false"
val TRUE_OR_FALSE_EXPERIMENT_NAME = "true or false"
val TRUE_OR_FALSE_EXPERIMENT = Experiment(TRUE_OR_FALSE_EXPERIMENT_NAME, listOf(OPTION_TRUE, OPTION_FALSE))

class ABTestingShould {

    @Rule @JvmField
    public val repeatRule: RepeatRule = RepeatRule()

    lateinit var randomGenerator: RandomGenerator
    lateinit var abTesting: ABTesting

    @Before
    fun setUp() {
        randomGenerator = mock(RandomGenerator::class.java)
        abTesting = ABTesting(
                listOf(YES_OR_NO_EXPERIMENT, TRUE_OR_FALSE_EXPERIMENT),
                ABTestingRepositoryFake(),
                randomGenerator)
    }

    @Test
    fun returnListOfExperiments() {
        val experiments: List<Experiment> = abTesting.experiments

        assertEquals(YES_OR_NO_EXPERIMENT_NAME, experiments[0].name)
    }

    @Test
    @Repeat(times = 1000)
    fun canWorkWithMultipleTests() {
        abTesting = ABTesting(
                listOf(YES_OR_NO_EXPERIMENT, TRUE_OR_FALSE_EXPERIMENT),
                ABTestingRepositoryFake(),
                RandomGenerator())
        abTesting.getCurrentOptions()
    }

    @Test
    fun returnListOfExperimentsWithCurrentOptions() {
        val currentOptions: List<CurrentOption> = abTesting.getCurrentOptions()
        given(randomGenerator.getRandom(anyInt())).willReturn(0)

        assertEquals(YES_OR_NO_EXPERIMENT_NAME, currentOptions[0].experiment)
        assertEquals(OPTION_YES, currentOptions[0].option)

        assertEquals(TRUE_OR_FALSE_EXPERIMENT_NAME, currentOptions[1].experiment)
        assertEquals(OPTION_TRUE, currentOptions[1].option)
    }

    @Test
    fun returnDetailOfExperiment() {
        val storedExperiment = abTesting.getExperiment(YES_OR_NO_EXPERIMENT_NAME)

        assertEquals(YES_OR_NO_EXPERIMENT_NAME, storedExperiment.name)
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
        val currentOption = abTesting.getCurrentOptionFor(YES_OR_NO_EXPERIMENT_NAME)
        mockRandomToReturn(1)

        val newOption = abTesting.getCurrentOptionFor(YES_OR_NO_EXPERIMENT_NAME)

        assertEquals(currentOption, newOption)
    }

    @Test
    fun giveRandomFirstOption() {
        abTesting = ABTesting(listOf(YES_OR_NO_EXPERIMENT), ABTestingRepositoryEmpty(), RandomGenerator())
        var yesCount = 0;
        var noCount = 0;

        for (i in 1..100) {
            when (abTesting.getCurrentOptionFor(YES_OR_NO_EXPERIMENT_NAME)) {
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