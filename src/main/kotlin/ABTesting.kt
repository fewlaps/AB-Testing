class ABTesting(val experiments: List<Experiment>) {

    fun getExperiment(name: String): Experiment {
        val found: Experiment? = experiments.find { it.name == name }
        return found ?: throw ExperimentNotFoundException()
    }

    fun getCurrentOptionFor(experimentName: String): String {
        TODO()
    }

}