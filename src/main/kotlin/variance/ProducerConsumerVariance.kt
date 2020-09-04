package variance

private class Producer<out T : Any>(val e: T) {
    fun read(): T = e
}

private class Consumer<in T : Any>() {
    private lateinit var e: T
    fun write(v: T): Unit {
        e = v
    }
}

//Double extends Number

fun main() {
    var p1: Producer<Number> = Producer<Double>(0.4)
    p1.read()
//    var p2: Producer<Double> = Producer<Number>(3)  // Disallowed
//    p2.read()
//    var c1: Consumer<Number> = Consumer<Double>()   // Disallowed
//    c1.write(3)
    var c2: Consumer<Double> = Consumer<Number>()
    c2.write(0.4)

    map1.put("1", Data<State<String>>()) //it does work because of Data<out State<*>>>. If `out` removed it doesn't
    map2.put("2", Data<State<String>>()) //it works
    map2.put("3", Data<State<Int>>()) //it works
}

/**
https://stackoverflow.com/questions/55662220/star-projection-in-kotlin
 */

private val map1 = mutableMapOf<String, Data<out State<*>>>()
private val map2 = mutableMapOf<String, Data<*>>()

private class Data<T>
private class State<T>