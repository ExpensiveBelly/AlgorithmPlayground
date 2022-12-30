package adventofcode2022

import Resources
import junit.framework.Assert.assertEquals
import org.junit.Test

class Day1_2022Test {

    private val input by lazy { Resources.resourceAsList("adventofcode2022/Day1_2022.txt") }

    @Test
    fun `Find the Elf carrying the most Calories`() {
        val caloriesCarried = calculateCaloriesCarriedByEachElf()
        assertEquals(67027, caloriesCarried.maxOf { it })
    }

    private fun calculateCaloriesCarriedByEachElf(): Collection<Int> {
        var counter = 0
        val caloriesCarried = mutableMapOf<Int, List<Int>>()
        input.forEach { food: String ->
            if (food.isEmpty()) counter++
            else caloriesCarried[counter] =
                (caloriesCarried[counter] ?: emptyList()) + food.toInt()
        }
        return caloriesCarried.mapValues { it.value.sum() }.values
    }

    @Test
    fun `Find the top three Elves carrying the most Calories`() {
        assertEquals(197291, calculateCaloriesCarriedByEachElf().sortedDescending().take(3).sum())
    }
}