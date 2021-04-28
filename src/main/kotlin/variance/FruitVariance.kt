package variance

/**
 * https://stackoverflow.com/questions/2662369/covariance-and-contravariance-real-world-example/35897887#35897887
 */

class FruitVariance {
    interface ICovariant<out T>
    interface IContravariant<in T>

    class Covariant<T> : ICovariant<T>
    class Contravariant<T> : IContravariant<T>

    open class Fruit
    class Apple : Fruit()

    class TheInsAndOuts {
        fun covariance() {
            val fruit: ICovariant<Fruit> = Covariant<Fruit>()
            val apple: ICovariant<Apple> = Covariant<Apple>()

            covariant(fruit)
            covariant(apple) //apple is being upcasted to fruit, without the out keyword this will not compile
        }

        fun contravariance() {
            val fruit: IContravariant<Fruit> = Contravariant<Fruit>()
            val apple: IContravariant<Apple> = Contravariant<Apple>()

            contravariant(fruit) //fruit is being downcasted to apple, without the in keyword this will not compile
            contravariant(apple)
        }

        fun covariant(fruit: ICovariant<Fruit>) {}
        fun contravariant(apple: IContravariant<Apple>) {}
    }
}