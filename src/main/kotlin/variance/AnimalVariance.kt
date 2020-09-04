package variance

/**
 * https://gist.github.com/jskierbi/273c53c384834c6aaf1d40c9c3197e6f
 */

class AnimalVariance {

    // https://kotlinlang.org/docs/reference/generics.html
    // The Existential Transformation: Consumer in, Producer out! :-)

    open class Animal
    class Lion : Animal()

    fun covariance() {
        // Covariance (producer)
        val covariantList: MutableList<out Animal> = mutableListOf<Lion>() // In Java: List<? extends Animal>
        val animal: Animal = covariantList.get(0) // Ok
//        covariantList.add(Animal()) // Compilation error - compiler doesn't know exact type of covariantList and is not sure if it can store Animal (not all animals are lions)
    }

    fun contravariance() {
        // Contravariance (consumer)
        val contravariantList: MutableList<in Animal> = mutableListOf<Any>() // In Java: List<? super Animal>
//        val animal: Animal = contravariantList[0]    // Compilation error - list can store other objects than Animals as well
        contravariantList.add(Animal()) // Ok
        contravariantList.add(Lion()) // Ok
    }

    fun producer() {
        // Producer is covariant in T
        // Class body can only return T, not take T as parameter
        abstract class Producer<out T> {
            abstract fun produce(): T // ok
//            abstract fun consume(item: T) // compile error
        }

        // Then it's possible to:
        fun covariantExample(x: Producer<String>) {
            val y: Producer<Any> = x // Allowed, producer is covariant in T
        }

    }

    fun consumer() {
        // Consumer is contravariant in T
        // Class body can only consume T (take as parameter), not return it
        abstract class Consumer<in T> {
//            abstract fun produce(): T // Compilation error
            abstract fun consume(item: T) // ok
        }

        // Then it's possible to:
        fun contravariantExample(x: Consumer<Number>) {
            val y: Consumer<Double> = x // Allowed, Consumer is contravariant in T
        }
    }
}