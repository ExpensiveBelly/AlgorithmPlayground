package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * https://adventofcode.com/2020/day/7
 */

class Day7Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day7.txt")
            .map { s ->
                s.split("contain")
                    .map {
                        it.replace("bags", "")
                            .replace("bag", "")
                            .replace(".", "")
                            .trim()
                    }
            }.map { it.first() to it.drop(1) }.toMap()
            .mapValues { entry -> entry.value.flatMap { it.split(",") }.map { it.trim() } }
    }

    @Test
    fun `part 1`() {
        assertEquals(
            335,
            parentsOf(setOf("shiny gold")).size - 1
        ) // -1 because we don't want to include "shiny gold" in the set
    }

    private tailrec fun parentsOf(direct: Set<String>, acc: Set<String> = emptySet()): Set<String> =
        if (direct.isEmpty()) acc
        else parentsOf(input.filterValues { list ->
            list.any { s ->
                direct.any { it in s }
            }
        }.keys, acc + direct)
}