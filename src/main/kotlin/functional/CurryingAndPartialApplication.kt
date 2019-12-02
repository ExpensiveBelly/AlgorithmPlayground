package functional

class CurryingAndPartialApplication {

    fun currying() {
        fun add(a: Int) = { b: Int -> a + b }
        add(1)(2) // returns 3.
    }

    fun `partial application`() {
        fun add(a: Int, b: Int) = a + b
        val addOne = { b: Int -> add(1, b) }

        val val1 = addOne(2) // returns 3
    }
}

fun main() {
    CurryingAndPartialApplication().run {
        currying()
        `partial application`()
    }
}