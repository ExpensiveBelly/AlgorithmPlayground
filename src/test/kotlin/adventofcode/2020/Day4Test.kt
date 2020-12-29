package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

class Day4Test {

    private val input by lazy { Resources.resourceAsList("adventofcode/2020/Day4.txt") }

    @Test
    fun `part 1`() {
        val expectedFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        assertEquals(192,
            input.asSequence()
                .chunked { last, _ -> last.isEmpty() }
                .map { list: List<String> -> list.toListOfPairs().flatten() }
                .count { list ->
                    list.map { it.first }.containsAll(expectedFields)
                })
    }

    @Test
    fun `part 2`() {

    }
}

private fun List<String>.toListOfPairs(): List<List<Pair<String, String>>> =
    map { s ->
        s.split(" ")
            .map { it.split(":", limit = 2) }
            .map { it.first() to it.last() }
    }

/*
https://discuss.kotlinlang.org/t/splitting-sequence-in-chunks-based-on-predicate/19005/5
 */

private fun <T> Sequence<T>.chunked(predicate: (T, T) -> Boolean): Sequence<List<T>> {
    val underlyingSequence = this
    return sequence {
        val buffer = mutableListOf<T>()
        var last: T? = null
        for (current in underlyingSequence) {
            val shouldSplit = last?.let { predicate(it, current) } ?: false
            if (shouldSplit) {
                yield(buffer.toList())
                buffer.clear()
            }
            buffer.add(current)
            last = current
        }
        if (buffer.isNotEmpty()) {
            yield(buffer)
        }
    }
}
