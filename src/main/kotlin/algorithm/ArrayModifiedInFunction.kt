package algorithm


class ArrayModifiedInFunction {

    fun execute(program: IntArray) {
        program[0] = -1
    }
}

fun main() {
    val program: IntArray = intArrayOf(1, 2, 3)

    ArrayModifiedInFunction().execute(program)

    println(program.toList())
}