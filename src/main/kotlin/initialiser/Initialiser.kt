package initialiser

/*
https://stackoverflow.com/questions/50222139/kotlin-calling-non-final-function-in-constructor-works

https://gist.github.com/ajalt/17645d90427774a0fca3048a6c7482e2
 */

abstract class Parent<T> {

    abstract val content: T

    init {
        println("@@@ init Parent")
        initialise(content)
        println("@@@ after init Parent")
    }

    private fun initialise(content: T) {
        println("Initialising $content")
    }
}

class Child1 : Parent<String>() {
    override val content: String
        get() = "Child1".also { println("@@@ Child 1 content") }
}

class Child2 : Parent<String>() {
    override val content: String = "Child2"
}

fun main() {
    Child1()
    Child2()
}