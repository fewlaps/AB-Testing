package doubles;

import ABTestingRepository

class ABTestingRepositoryEmpty : ABTestingRepository {

    override fun getOption(experiment: String): String? {
        return null
    }

    override fun saveOption(experiment: String, option: String) {

    }
}