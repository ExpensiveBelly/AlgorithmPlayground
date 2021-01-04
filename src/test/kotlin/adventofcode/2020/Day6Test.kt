package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

/**
https://adventofcode.com/2020/day/6
 */

class Day6Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day6.txt")
            .asSequence()
            .chunked { last, _ -> last.isEmpty() }
            .map { list -> list.filter { it.isNotEmpty() } }
            .toList()
    }

    @Test
    fun `part 1`() {
        assertEquals(
            6768,
            input.map {
                it.joinToString(separator = "")
                    .toCharArray()
                    .distinct()
                    .count()
            }.sum()
        )
    }

    @Test
    fun `part 2`() {
        assertEquals(
            3489,
            input.fold(0, { acc: Int, list: List<String> ->
                if (list.size == 1) acc + list.first().count()
                else {
                    val head = list.first().toCharArray().toList()
                    val tail = list.drop(1)
                    acc + head.fold(0, { count: Int, c: Char ->
                        if (tail.all { it.contains(c) }) count.inc() else count
                    })
                }
            })
        )
    }
}