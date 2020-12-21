package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

class Day2Test {

    private val input = Resources.resourceAsList("adventofcode/Day2.txt").map {
        val (a, b, c) = it.split(" ", limit = 3)
        val (min, max) = a.split("-", limit = 2)
        Data(min.toInt(), max.toInt(), b.toCharArray().first(), c)
    }

    @Test
    fun `part 1`() {
        val result = input.sumBy { data ->
            val amountOfLetters = data.password.filter { it == data.letter }.length
            (amountOfLetters in (data.min..data.max)).toInt()
        }

        assertEquals(538, result)
    }

    @Test
    fun `part 2`() {
        val result = input.sumBy { data ->
            (listOfNotNull(
                data.password.getOrNull(data.min.toOneIndex()),
                data.password.getOrNull(data.max.toOneIndex())
            ).filter { it == data.letter }.count() == 1).toInt()
        }

        assertEquals(489, result)
    }
}

private fun Int.toOneIndex(): Int = this - 1

private fun Boolean.toInt() = if (this) 1 else 0

private data class Data(val min: Int, val max: Int, val letter: Char, val password: String)