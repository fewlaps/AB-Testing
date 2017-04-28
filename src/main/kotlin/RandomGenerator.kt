import java.util.*

open class RandomGenerator {

    open fun getRandom(bound : Int) = Random().nextInt(bound)
}