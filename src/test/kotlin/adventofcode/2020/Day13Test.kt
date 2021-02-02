package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test


class Day13Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day13.txt")
    }

    @Test
    fun `part 1`() {
        val timestamp = input.first().toInt()
        val buses =
            input.last().split(",")
                .filterNot { it.equals("x", ignoreCase = true) }
                .map { it.toInt() }
                .sorted()

        val busTimes = buses.map { bus ->
            bus to generateSequence(bus) { it + bus }.takeWhile { it < timestamp + bus }.last()
        }.toMap()

        val entry = busTimes.minBy { it.value }!!
        assertEquals(102, entry.key * entry.value.minus(timestamp))
    }
}