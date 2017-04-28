import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

val YES_OR_NO_EXPERIMENT = Experiment("yes or no", listOf("yes", "no"))

class ABTestingShould {

    lateinit var randomGenerator : RandomGenerator
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
    fun returnFirstOption_whenRandomIsLessThanHalf() {
        given(randomGenerator.getRandom()).willReturn(0.25)

        val resultOption = abTesting.getCurrentOptionFor("yes or no")

        assertEquals("yes", resultOption)
    }

    @Test
    fun returnSecondOption_whenRandomIsMoreThanHalf() {
        given(randomGenerator.getRandom()).willReturn(0.75)

        val resultOption = abTesting.getCurrentOptionFor("yes or no")

        assertEquals("no", resultOption)
    }
}