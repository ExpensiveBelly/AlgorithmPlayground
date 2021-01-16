package adventofcode.`2020`

import Resources
import org.junit.Test
import kotlin.math.absoluteValue

private const val INVALID_INDEX = -1

class Day9Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day9.txt").map { it.toLong() }
    }

    @Test
    fun `part 1`() {
        println(checkEncoding(input, 25))
    }
}

private tailrec fun checkEncoding(input: List<Long>, preamble: Int): Long {
    val number = input.getOrNull(preamble) ?: return INVALID_INDEX.toLong()
    val preambleSet = input.asSequence().take(preamble).toSet()
    return if (preambleSet.none { n -> preambleSet.any { (number - n - it).absoluteValue == 0L } }) number
    else checkEncoding(input.drop(1), preamble)
}