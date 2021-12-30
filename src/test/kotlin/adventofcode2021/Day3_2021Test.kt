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
        val message = input.fold(
            buildList {
                addAll(input.first().map { BinaryCounter() })
            }.toMutableList(),
            { acc: MutableList<BinaryCounter>, s: String ->
                acc.apply {
                    s.forEachIndexed { index, c ->
                        set(index, acc[index].inc(c))
                    }
                }
            })

        assertEquals(
            3882564,
            message.decimalNumber { it.max() } * message.decimalNumber { it.min() })
    }

    private fun MutableList<BinaryCounter>.decimalNumber(transform: (BinaryCounter) -> Char): Long =
        map { transform(it) }.joinToString("").toDecimalNumber()
}