package adventofcode.`2020`

import Resources
import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
https://adventofcode.com/2020/day/1
 */

class Day1Test {

    @Test
    fun `Part 1 - find pair that sums 2020`() {
        val input = Resources.resourceAsList("adventofcode/2020/Day1.txt")

        val (first, second) = input.map { it.toInt() }.pair2020()

        assertEquals(806656, first * second)
    }

    @Test
    fun `Part 2 - find triple that sums 2020`() {
        val input = Resources.resourceAsList("adventofcode/2020/Day1.txt")

        val (first, second, third) = input.map { it.toInt() }.triple2020()

        assertEquals(230608320, first * second * third)
    }
}

private fun List<Int>.pair2020(): Pair<Int, Int> {
    forEachIndexed { index, i ->
        drop(index + 1).forEach { j ->
            if (i + j == 2020) return i to j
        }
    }
    throw IllegalStateException()
}

private fun List<Int>.triple2020(): Triple<Int, Int, Int> {
    forEachIndexed { indexI, i ->
        drop(indexI + 1).forEachIndexed { indexJ, j ->
            drop(indexJ + 1).forEachIndexed { indexK, k ->
                if (i + j + k == 2020) return Triple(i, j, k)
            }
        }
    }
    throw IllegalStateException()
}
