package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test


class Day10Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day10.txt").map { it.toInt() }
    }

    @Test
    fun `part 1`() {
        val differences = accumulateDifferences(input, 0).addBuiltInAdapter()
        assertEquals(2176, differences.oneJoltDifferences * differences.threeJoltsDifferences)
    }

    private tailrec fun accumulateDifferences(
        input: List<Int>,
        currentJolt: Int,
        acc: Differences = Differences(0, 0)
    ): Differences =
        if (input.isEmpty()) acc
        else {
            val newJolt = input.filter { it in (currentJolt..currentJolt + 3) }.min()!!
            accumulateDifferences(
                input.toMutableList().apply { remove(newJolt) },
                newJolt,
                acc.addDifference(currentJolt, newJolt)
            )
        }
}

private data class Differences(var oneJoltDifferences: Int, var threeJoltsDifferences: Int)

private fun Differences.addBuiltInAdapter() = copy(threeJoltsDifferences = threeJoltsDifferences.inc())
private fun Differences.addDifference(currentJolt: Int, newJolt: Int): Differences = when (newJolt - currentJolt) {
    1 -> copy(oneJoltDifferences = oneJoltDifferences.inc())
    3 -> copy(threeJoltsDifferences = threeJoltsDifferences.inc())
    else -> this
}
