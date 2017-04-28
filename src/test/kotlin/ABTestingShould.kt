import org.junit.Assert.assertEquals
import org.junit.Test

class ABTestingShould {

    @Test
    internal fun returnListOfExperiments() {
        val experiment = Experiment("yes or no", listOf(Option("yes"), Option("no")))
        val abTesting = ABTesting(listOf(experiment))

        val experiments: List<Experiment> = abTesting.experiments

        assertEquals("yes or no", experiments[0].name)
    }

    @Test
    fun returnDetailOfExperiment() {
        val experiment = Experiment("yes or no", listOf(Option("yes"), Option("no")))
        val abTesting = ABTesting(listOf(experiment))

        val storedExperiment = abTesting.getExperiment("yes or no")

        assertEquals("yes or no", storedExperiment.name)
        assertEquals("yes", storedExperiment.options[0].name)
        assertEquals("no", storedExperiment.options[1].name)
    }


    @Test(expected = ExperimentNotFoundException::class)
    fun failWhenExperimentDoesntExists() {
        val experiment = Experiment("yes or no", listOf(Option("yes"), Option("no")))
        val abTesting = ABTesting(listOf(experiment))

        abTesting.getExperiment("a or b")
    }
}