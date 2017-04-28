class ABTesting(val experiments: List<Experiment>, val randomGenerator: RandomGenerator = RandomGenerator()) {

    fun getExperiment(name: String): Experiment {
        val found: Experiment? = experiments.find { it.name == name }
        return found ?: throw ExperimentNotFoundException()
    }

    fun getCurrentOptionFor(experimentName: String): String {
        val randomValue = randomGenerator.getRandom(experiments.size)
        return getExperiment(experimentName).options[randomValue]
    }

}