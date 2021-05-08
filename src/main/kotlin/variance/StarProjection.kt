package variance

/*
https://typealias.com/guides/star-projections-and-how-they-work/
 */

class StarProjection {

    init {
        val listOfStrings: List<String> = listOf("Hello", "Kotlin", "World")
        val arrayOfStrings = arrayOf("Hello", "Kotlin", "World")

        acceptAnyList(listOfStrings)
        acceptStarList(listOfStrings)
//        acceptAnyArray(arrayOfStrings)  // Compiler error here
        acceptStarArray(arrayOfStrings)
    }

    fun acceptAnyList(list: List<Any?>) {}
    fun acceptStarList(list: List<*>) {}
    fun acceptAnyArray(array: Array<Any?>) {}
    fun acceptStarArray(array: Array<*>) {}

    /**
     * Why use * instead of specifying a type?
     *
     * https://stackoverflow.com/questions/48172314/kotlin-star-projection-on-contravariant-types
     *
     * We want to keep the type unknown, so that we won't end up using the T specific functions
     * accidently. For example, in the above function, if we are allowed to call the
     * list1.add(Something()), we might end up mutating the list accidentally where we just intend
     * to compare the lists. So, the compiler will help us by flagging an error when we call
     * the add() function. Apart from creating the safety, our function will also be reusable for
     * various types, not just some specific type.
     */

    fun getBiggerOfTwo(list1: MutableList<*>, list2: MutableList<*>): MutableList<*> {
        return if (list1.size >= list2.size) list1 else list2
    }
}

private class Foo<out U : Number> {
    private var u: U? = null

    fun produce(): U? {
        return u
    }
}

private fun usingFoo(foo: Foo<*>) {
    foo.produce()
}

fun main() {
    usingFoo(Foo<Number>()) //OK
//    usingFoo(Foo<Any>()) // Not OK

//Direct calls (not using method with *)
    Foo<Number>().produce() //OK
//    Foo<Any>().produce() //Not OK

}