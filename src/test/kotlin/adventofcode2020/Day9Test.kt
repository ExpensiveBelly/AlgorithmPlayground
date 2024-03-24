package adventofcode2020

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.absoluteValue

private const val INVALID_INDEX = -1L

class Day9Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode2020/Day9.txt").map { it.toLong() }
    }

    @Test
    fun `part 1`() {
        assertEquals(104054607, checkEncoding(input, 25))
    }

    @Test
    fun `part 2`() {
        val element = checkEncoding(input, 25)
        val invalidNumberIndex = input.indexOf(element)
        val inputWithoutInvalidNumber = input.apply {
            toMutableList().removeAt(invalidNumberIndex)
        }
        assertEquals(13935797, findEncryptionWeakness(inputWithoutInvalidNumber, element))
    }

    private tailrec fun findEncryptionWeakness(input: List<Long>, element: Long): Long {
        var sum = 0L
        val list = input.takeWhile {
            sum += it
            sum <= element
        }
        return if (list.sum() == element) {
            list.maxOrNull()?.let { max ->
                list.minOrNull()?.let { min ->
                    min + max
                }
            } ?: INVALID_INDEX
        } else findEncryptionWeakness(input.drop(1), element)
    }
}

private tailrec fun checkEncoding(input: List<Long>, preamble: Int): Long {
    val number = input.getOrNull(preamble) ?: return INVALID_INDEX
    val preambleSet = input.asSequence().take(preamble).toSet()
    return if (preambleSet.none { n -> preambleSet.any { (number - n - it).absoluteValue == 0L } }) number
    else checkEncoding(input.drop(1), preamble)
}