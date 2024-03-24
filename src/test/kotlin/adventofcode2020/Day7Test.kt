package adventofcode2020

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * https://adventofcode.com/2020/day/7
 */

class Day7Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode2020/Day7.txt")
            .map { s ->
                s.split("contain")
                    .map {
                        it.replace("bags", "")
                            .replace("bag", "")
                            .replace(".", "")
                            .replace("no", "0")
                            .trim()
                    }
            }.map { it.first() to it.drop(1) }.toMap()
            .mapValues { entry ->
                entry.value.flatMap { it.split(",") }.map { it.trim() }
                    .map { Character.getNumericValue(it.first()) to it.drop(1).trim() }
            }.filterNot { entry -> entry.value.all { it.first == 0 } }
    }

    @Test
    fun `part 1`() {
        assertEquals(
            335,
            parentsOf(setOf("shiny gold")).size - 1
        ) // -1 because we don't want to include "shiny gold" in the set
    }

    @Test
    fun `part 2`() {
        assertEquals(2431, childrenOf(input["shiny gold"] ?: emptyList()) - 1)
    }

    private fun childrenOf(list: List<Pair<Int, String>>): Int =
        if (list.isEmpty()) 1
        else 1 + list.sumBy { (cost, child) ->
            cost * childrenOf(input[child] ?: emptyList())
        }

    private tailrec fun parentsOf(direct: Set<String>, acc: Set<String> = emptySet()): Set<String> =
        if (direct.isEmpty()) acc
        else parentsOf(input.filterValues { list ->
            list.any { s ->
                direct.any { it in s.second }
            }
        }.keys, acc + direct)
}