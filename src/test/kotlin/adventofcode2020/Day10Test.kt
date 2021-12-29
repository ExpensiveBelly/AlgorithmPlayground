package adventofcode2020

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

    @Test
    fun `part 2 copied`() {
        val adapters: List<Int> = input.plus(0).plus(input.maxOrNull()!! + 3).sorted()
        val pathsByAdapter: MutableMap<Int, Long> = mutableMapOf(0 to 1L)
        adapters.drop(1).forEach { adapter ->
            pathsByAdapter[adapter] = (1..3).map { lookBack ->
                pathsByAdapter.getOrDefault(adapter - lookBack, 0)
            }.sum()
        }

        assertEquals(18512297918464, pathsByAdapter.getValue(adapters.last()))
    }

    @Test
    fun `part 2`() {
        assertEquals(18512297918464, countCombinations(input, 0))
    }

    private fun countCombinations(
        input: List<Int>,
        currentJolt: Int,
        acc: Long = 0,
        cache: MutableMap<Int, Long> = mutableMapOf()
    ): Long = if (input.isEmpty()) acc
    else {
        val cachedValue = cache[currentJolt]
        if (cachedValue != null) cachedValue.also { println("Cache hit: $currentJolt : $cachedValue") }
        else {
            val newJolts = input.filter { it in (currentJolt..currentJolt + 3) }
            if (newJolts.isEmpty()) acc.inc().also { cache[currentJolt] = it }
            else newJolts.sumByLong { countCombinations(input.toMutableList().apply { remove(it) }, it, acc, cache) }
                .also { cache[currentJolt] = it }
        }
    }

    private inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
        var sum = 0L
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }

    private tailrec fun accumulateDifferences(
        input: List<Int>,
        currentJolt: Int,
        acc: Differences = Differences(0, 0)
    ): Differences =
        if (input.isEmpty()) acc
        else {
            val newJolt = input.filter { it in (currentJolt..currentJolt + 3) }.minOrNull()!!
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
