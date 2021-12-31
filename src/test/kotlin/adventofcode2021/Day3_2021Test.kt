package adventofcode2021

import Resources
import adventofcode2020.toDecimalNumber
import org.junit.Assert.assertEquals
import org.junit.Test

class Day3_2021Test {

    private val input =
        Resources.resourceAsList("adventofcode2021/Day3_2021.txt")

    private data class BinaryCounter(val zeroes: Int = 0, val ones: Int = 0) {

        fun inc(ch: Char): BinaryCounter =
            if (ch == '0') copy(zeroes = zeroes.inc()) else copy(ones = ones.inc())

        fun max(): Char = if (zeroes > ones) '0' else '1'

        fun min(): Char = if (zeroes > ones) '1' else '0'
    }

    @Test
    fun `part 1`() {
        val binaryCounters = input.toBinaryCounters()

        assertEquals(
            3882564,
            binaryCounters.decimalNumber { it.max() } * binaryCounters.decimalNumber { it.min() })
    }

    private fun List<BinaryCounter>.decimalNumber(transform: (BinaryCounter) -> Char): Long =
        map { transform(it) }.joinToString("").toDecimalNumber()

    @Test
    fun `part 2`() {
        var filteredInputOxygenGenerator = input
        var filteredInputCo2ScrubberRating = input
        var oxygenGeneratorRatingFound = false
        var co2ScrubberRatingFound = false
        var i = 0
        while (!oxygenGeneratorRatingFound || !co2ScrubberRatingFound) {
            val max =
                filteredInputOxygenGenerator.toBinaryCounters()[i].max() //This is very inefficient because it's calculated for the whole list rather than just for that column
            val min = filteredInputCo2ScrubberRating.toBinaryCounters()[i].min()

            if (!oxygenGeneratorRatingFound) {
                filteredInputOxygenGenerator = filteredInputOxygenGenerator.filter { it[i] == max }
                if (filteredInputOxygenGenerator.size == 1) oxygenGeneratorRatingFound = true
            }
            if (!co2ScrubberRatingFound) {
                filteredInputCo2ScrubberRating =
                    filteredInputCo2ScrubberRating.filter { it[i] == min }
                if (filteredInputCo2ScrubberRating.size == 1) co2ScrubberRatingFound = true
            }

            i++
        }
        val oxygenGenerator = filteredInputOxygenGenerator.joinToString("")
        val co2ScrubberRating = filteredInputCo2ScrubberRating.joinToString("")

        assertEquals(
            3385170,
            (oxygenGenerator.toDecimalNumber() *
                    co2ScrubberRating.toDecimalNumber())
        )
    }

    private fun List<String>.toBinaryCounters() = fold(
        buildList {
            this.addAll(this@toBinaryCounters.first().map { BinaryCounter() })
        }.toMutableList(),
        { acc: MutableList<BinaryCounter>, s: String ->
            acc.apply {
                s.forEachIndexed { index, c ->
                    set(index, acc[index].inc(c))
                }
            }
        }).toList()
}