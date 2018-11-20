package com.fewlaps.abtesting.core

class ABTesting(val experiments: List<Experiment>, val repository: ABTestingRepository, val randomGenerator: RandomGenerator = RandomGenerator()) {

    fun getExperiment(name: String): Experiment {
        val found: Experiment? = experiments.find { it.name == name }
        return found ?: throw ExperimentNotFoundException()
    }

    fun getCurrentOptionFor(experimentName: String): String {
        val storedOption = repository.getOption(experimentName);
        if (storedOption != null) {
            return storedOption
        }

        val experiment = getExperiment(experimentName)
        val randomValue = randomGenerator.getRandom(experiment.options.size)
        val option = experiment.options[randomValue]
        repository.saveOption(experimentName, option)
        return option
    }

    fun getCurrentOptions(): List<CurrentOption> {
        return experiments.map {
            experiment ->  CurrentOption(experiment.name, getCurrentOptionFor(experiment.name))
        }
    }
}