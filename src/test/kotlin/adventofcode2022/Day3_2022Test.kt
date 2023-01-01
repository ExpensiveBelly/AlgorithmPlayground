package adventofcode2022

import Resources
import org.junit.Test

class Day3_2022Test {

    private val input by lazy { Resources.resourceAsList("adventofcode2022/Day3_2022.txt") }

    @Test
    fun `Find the item type that appears in both compartments of each rucksack`() {
        println(input.map {
            val firstCompartment = it.slice(0 until it.length / 2).toCharArray().toSet()
            val secondCompartment = it.slice(it.length / 2 until it.length).toCharArray().toSet()
            firstCompartment.intersect(secondCompartment)
        })
    }
}