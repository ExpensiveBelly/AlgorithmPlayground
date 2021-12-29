package adventofcode2021

import Resources
import junit.framework.TestCase.assertEquals
import org.junit.Test

class Day1_2021Test {

    private val input by lazy { Resources.resourceAsList("adventofcode2021/Day1_2021.txt") }

    @Test
    fun `Part 1 count the number of times a depth measurement increases`() {
        assertEquals(1154, input.map { it.toInt() }
            .zipWithNext()
            .count { (a, b) -> b > a })
    }

    @Test
    fun `Part 2 count the number of times the sum of measurements in this three-measurement sliding window increases`() {
        assertEquals(1127, input.asSequence().map { it.toInt() }
            .windowed(3, partialWindows = false)
            .map { it.sum() }
            .zipWithNext()
            .count { (a, b) -> b > a })
    }
}