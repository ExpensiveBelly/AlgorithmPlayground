package adventofcode2022

import Resources
import junit.framework.TestCase.assertEquals
import org.junit.Test

private val Char.priority: Int
    get() = if (isLetter()) {
        if (isLowerCase()) code - 96 else code - 38
    } else throw IllegalStateException()

class Day3_2022Test {

    private val input by lazy { Resources.resourceAsList("adventofcode2022/Day3_2022.txt") }

    @Test
    fun `Find the item type that appears in both compartments of each rucksack`() {
        assertEquals(8088, input.map {
            val firstCompartment = it.slice(0 until it.length / 2).toCharArray().toSet()
            val secondCompartment = it.slice(it.length / 2 until it.length).toCharArray().toSet()
            intersect(listOf(firstCompartment, secondCompartment))
        }.sumOf { it.priority })
    }

    private fun intersect(sections: List<Set<Char>>): Char =
        sections.reduce { acc, chars -> acc.intersect(chars) }.first()

    @Test
    fun `Find the item type that corresponds to the badges of each three-Elf group`() {
        assertEquals(2522,
            input.chunked(3).map { groups -> intersect(groups.map { it.toCharArray().toSet() }) }
                .sumOf { it.priority })
    }
}