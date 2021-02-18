package adventofcode.`2020`

import org.junit.Test


class Day15Test {

    private val input by lazy { listOf(1, 20, 11, 6, 12, 0) }

    @Test
    fun `part 1`() {
        println(generateSequence(input) {
            it + listOf(it.nextNumber())
        }.take(2020 - (input.size - 1)).toList().last().last())
    }
}

private fun List<Int>.nextNumber(): Int {
    val index = dropLast(1).indexOfLast { it == last() }
    return if (index == -1) 0 else size - (index + 1)
}
