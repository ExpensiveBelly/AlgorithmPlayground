package adventofcode2021

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

class Day2_2021Test {

    private data class Measurements(
        val horizontalPosition: Int = 0,
        val depth: Int = 0,
        val aim: Int = 0
    )

    private val input =
        Resources.resourceAsList("adventofcode2021/Day2_2021.txt").map { it.split(" ", limit = 2) }
            .map { it.first() to Integer.valueOf(it.last()) }

    @Test
    fun `part 1 multiply your final horizontal position by your final depth`() {
        val (horizontalPosition, depth) = input.fold(
            Measurements(),
            { acc: Measurements, (command, units) ->
                when (command) {
                    "forward" -> acc.copy(horizontalPosition = acc.horizontalPosition + units)
                    "down" -> acc.copy(depth = acc.depth + units)
                    "up" -> acc.copy(depth = (acc.depth - units).coerceAtLeast(0))
                    else -> throw IllegalStateException()
                }
            })
        assertEquals(1727835, horizontalPosition * depth)
    }

    @Test
    fun `part 2 using this new interpretation of the commands, multiply your final horizontal position by your final depth`() {
        val (horizontalPosition, depth, _) = input.fold(
            Measurements(),
            { acc: Measurements, (command, units) ->
                when (command) {
                    "forward" -> acc.copy(
                        horizontalPosition = acc.horizontalPosition + units,
                        depth = acc.depth + (acc.aim * units)
                    )
                    "down" -> acc.copy(aim = acc.aim + units)
                    "up" -> acc.copy(aim = (acc.aim - units).coerceAtLeast(0))
                    else -> throw IllegalStateException()
                }
            })

        assertEquals(1544000595, horizontalPosition * depth)
    }
}