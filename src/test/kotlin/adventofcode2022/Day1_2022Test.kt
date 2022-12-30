package adventofcode2022

import adventofcode2020.Filename
import adventofcode2020.readChunkedLines
import junit.framework.TestCase.assertEquals
import org.junit.Test

class Day1_2022Test {

    private val input: List<List<String>> by lazy { Filename("adventofcode2022/Day1_2022.txt").readChunkedLines() }

    @Test
    fun `Find the Elf carrying the most Calories`() {
        val caloriesCarried = calculateCaloriesCarriedByEachElf()
        assertEquals(67027, caloriesCarried.first())
    }

    private fun calculateCaloriesCarriedByEachElf(): List<Int> {
        return input.map { calories -> calories.sumOf { it.toInt() } }.sortedDescending()
    }

    @Test
    fun `Find the top three Elves carrying the most Calories`() {
        assertEquals(197291, calculateCaloriesCarriedByEachElf().take(3).sum())
    }
}