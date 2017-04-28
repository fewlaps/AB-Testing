import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

val YES_OR_NO_EXPERIMENT = Experiment("yes or no", listOf("yes", "no"))

class ABTestingShould {

    lateinit var randomGenerator: RandomGenerator
    lateinit var abTesting: ABTesting

    @Before
    fun setUp() {
        randomGenerator = mock(RandomGenerator::class.java)
        abTesting = ABTesting(listOf(YES_OR_NO_EXPERIMENT), randomGenerator)
    }

    @Test
    internal fun returnListOfExperiments() {
        val experiments: List<Experiment> = abTesting.experiments

        assertEquals("yes or no", experiments[0].name)
    }

    @Test
    fun returnDetailOfExperiment() {
        val storedExperiment = abTesting.getExperiment("yes or no")

        assertEquals("yes or no", storedExperiment.name)
        assertEquals("yes", storedExperiment.options[0])
        assertEquals("no", storedExperiment.options[1])
    }

    @Test(expected = ExperimentNotFoundException::class)
    fun failWhenExperimentDoesntExists() {
        abTesting.getExperiment("a or b")
    }

    @Test
    fun manageMultipleOptionsWithSameChance() {
        val experimentName = "three options experiment"
        val experiment = Experiment(experimentName, listOf("a", "b", "c"))
        abTesting = ABTesting(listOf(experiment), randomGenerator)

        assertRandomValueGivesOption(experimentName, 0, "a")
        assertRandomValueGivesOption(experimentName, 1, "b")
        assertRandomValueGivesOption(experimentName, 2, "c")
    }

    private fun assertRandomValueGivesOption(experimentName: String, random: Int, expectedOption: String) {
        given(randomGenerator.getRandom(anyInt())).willReturn(random)
        val resultOption = abTesting.getCurrentOptionFor(experimentName)
        assertEquals(expectedOption, resultOption)
    }
}