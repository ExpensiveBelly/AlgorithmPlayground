package adventofcode.`2020`

import org.junit.Assert.assertEquals
import org.junit.Test


class Day15Test {

    private val input by lazy { listOf(1, 20, 11, 6, 12, 0) }

    @Test
    fun `part 1`() {
        assertEquals(1085, input.generateSpokenNumber(2020))
    }

    @Test
    fun `part 2`() {
        assertEquals(10652, input.generateSpokenNumber(30_000_000))
    }

    private fun List<Int>.generateSpokenNumber(n: Int): Int {
        val lastSpokenMap = mutableMapOf<Int, Int>()
        dropLast(1).forEachIndexed { index, lastSpokenNumber -> lastSpokenMap[lastSpokenNumber] = index + 1 }
        return generateSequence(size to last()) { (turn, lastNumberSpoken) ->
            val lastPosition = lastSpokenMap[lastNumberSpoken]
            lastSpokenMap[lastNumberSpoken] = turn
            if (lastPosition == null) turn.inc() to 0
            else turn.inc() to (turn - lastPosition)
        }.take(n - (size - 1)).last().second
    }
}

private fun List<Int>.nextNumberTooSlow(): Int {
    val index = dropLast(1).indexOfLast { it == last() }
    return if (index == -1) 0 else size - (index + 1)
}
