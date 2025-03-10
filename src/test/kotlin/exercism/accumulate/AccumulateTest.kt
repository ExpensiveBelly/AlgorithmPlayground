package exercism.accumulate

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class AccumulateTest {

    @Test
    fun emptyAccumulateProducesEmptyAccumulation() {
        val input = listOf<Int>()
        val expectedOutput = listOf<Int>()
        assertEquals(expectedOutput, Accumulate.accumulate(input, { x -> x * x }))
    }

    @Test
    fun accumulateSquares() {
        val input = listOf(1, 2, 3)
        val expectedOutput = listOf(1, 4, 9)
        assertEquals(expectedOutput, Accumulate.accumulate(input, { x -> x * x }))
    }

    @Test
    fun accumulateUpperCases() {
        val input = listOf("hello", "world")
        val expectedOutput = listOf("HELLO", "WORLD")
        assertEquals(expectedOutput, Accumulate.accumulate(input, { it.uppercase(Locale.getDefault()) }))
    }

    @Test
    fun accumulateReversedStrings() {
        val input = "the quick brown fox etc".split(" ")
        val expectedOutput = "eht kciuq nworb xof cte".split(" ")
        assertEquals(expectedOutput, Accumulate.accumulate(input, { it.reversed() }))
    }

    @Test
    fun accumulateWithinAccumulate() {
        val input1 = listOf("a", "b", "c")
        val input2 = listOf("1", "2", "3")
        val expectedOutput = listOf("a1 a2 a3", "b1 b2 b3", "c1 c2 c3")
        assertEquals(expectedOutput, Accumulate.accumulate(input1,
            { c -> Accumulate.accumulate(input2, { d -> c + d }).joinToString(" ") }
        ))
    }

    @Test
    fun accumulateToDifferentType() {
        val input = listOf(1, 2, 3)
        val expectedOutput = listOf("1", "2", "3")
        assertEquals(expectedOutput, Accumulate.accumulate(input, { it.toString() }))
    }
}
