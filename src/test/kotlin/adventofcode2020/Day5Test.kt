package adventofcode2020

import Resources
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.pow

/**
https://adventofcode.com/2020/day/5
 */

class Day5Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day5.txt")
    }

    @Test
    fun `part 1`() {
        assertEquals(848, input.parallelStream()
            .map { it.toSeatId() }
            .max { o1, o2 -> o1.compareTo(o2) }.get()
        )
    }

    @Test
    fun `part 2`() {
        assertEquals(682, input
            .asSequence()
            .map { it.toSeatId() }
            .sorted()
            .windowed(3)
            .flatMap { (a, b, c) ->
                when {
                    b > a.inc() -> sequenceOf(a.inc())
                    c > b.inc() -> sequenceOf(b.inc())
                    else -> emptySequence()
                }
            }.first()
        )
    }
}

private fun String.toSeatId(): Int {
    val row = dropLast(3).calculateRow()
    val column = takeLast(3).calculateColumn()
    return row * 8 + column
}

private fun String.calculateColumn() = calculateValue('L')
private fun String.calculateRow() = calculateValue('F')

private fun String.calculateValue(frontRangeChar: Char): Int {
    val (a, b) = fold(
        0 to (2.toDouble().pow(length) - 1).toInt(), { (min, max): Pair<Int, Int>, c: Char ->
            val upperOrLower = (min + (max + 1)).absoluteValue / 2
            if (c == frontRangeChar) min to upperOrLower - 1 else upperOrLower to max
        })
    return min(a, b)
}
