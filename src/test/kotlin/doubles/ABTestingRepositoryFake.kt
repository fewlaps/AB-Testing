package doubles;

import ABTestingRepository
import java.util.*

class ABTestingRepositoryFake : ABTestingRepository {

    val repository: HashMap<String, String> = HashMap()

    override fun getOption(experiment: String): String? {
        return repository[experiment]
    }

    override fun saveOption(experiment: String, option: String) {
        repository.put(experiment, option)
    }
}