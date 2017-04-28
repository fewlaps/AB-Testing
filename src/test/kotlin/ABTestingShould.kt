import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

val YES_OR_NO_EXPERIMENT = Experiment("yes or no", listOf(Option("yes"), Option("no")))

class ABTestingShould {

    lateinit var abTesting: ABTesting

    @Before
    fun setUp() {
        abTesting = ABTesting(listOf(YES_OR_NO_EXPERIMENT))
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
        assertEquals("yes", storedExperiment.options[0].name)
        assertEquals("no", storedExperiment.options[1].name)
    }

    @Test(expected = ExperimentNotFoundException::class)
    fun failWhenExperimentDoesntExists() {
        abTesting.getExperiment("a or b")
    }

    @Test
    fun returnAnOptionForAnExperiment() {
        val result = abTesting.getCurrentOptionFor("yes or no")

        assertTrue(result == "yes" || result == "no")
    }
}