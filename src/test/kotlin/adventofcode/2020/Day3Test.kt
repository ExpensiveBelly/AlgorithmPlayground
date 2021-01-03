package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

/**
https://adventofcode.com/2020/day/3
 */

class Day3Test {

    private val input by lazy { Resources.resourceAsList("adventofcode/2020/Day3.txt") }

    @Test
    fun `part 1`() {
        assertEquals(178, countTrees(Slope(3, 1), input))
    }

    @Test
    fun `part 2`() {
        assertEquals(3_492_520_200, listOf(
            Slope(1, 1), // 78
            Slope(3, 1), // 178
            Slope(5, 1), // 75
            Slope(7, 1), // 86
            Slope(1, 2) // 39
        ).parallelStream()
            .map { countTrees(it, input).toLong() }
            .reduce { acc, i -> acc * i }
            .get()
        )
    }

    private tailrec fun countTrees(slope: Slope, input: List<String>, index: Int = 0, treeCount: Int = 0): Int {
        val droppedInput = input.drop(slope.down)
        return if (droppedInput.isEmpty()) treeCount
        else {
            val line = droppedInput.first()
            val shiftedIndex = (index + slope.right) % line.length
            countTrees(
                slope = slope,
                input = droppedInput,
                index = shiftedIndex,
                treeCount = if (line[shiftedIndex] == '#') treeCount + 1 else treeCount
            )
        }
    }
}

private data class Slope(val right: Int, val down: Int)