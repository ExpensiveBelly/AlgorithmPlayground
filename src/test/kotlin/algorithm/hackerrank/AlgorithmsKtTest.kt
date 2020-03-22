package algorithm.hackerrank

import Resources
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import java.math.BigInteger

class AlgorithmsKtTest {

    @Test
    fun `jumping on clouds`() {
        assertEquals(97, jumpingOnClouds(arrayOf(1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1), 19))
    }

    @Test
    fun `find digits`() {
        assertEquals(3, findDigits(1012))
    }

    @Test
    fun `extra long factorial`() {
        assertEquals(BigInteger("15511210043330985984000000"), extraLongFactorials(25))
    }

    @Test
    fun `cat and mouse`() {
        val input = Resources.resourceAsList("catAndMouseInput.txt")
        val output = Resources.resourceAsList("catAndMouseOutput.txt")

        output.zip(input).forEach { (output, input) ->
            val split = input.split(" ").map { it.toInt() }
            assertEquals(input, output, catAndMouse(split[0], split[1], split[2]))
        }
    }

    @Test
    fun `picking numbers`() {
        assertEquals(5, pickingNumbers(arrayOf(1, 1, 2, 2, 4, 4, 5, 5, 5)))
        assertEquals(3, pickingNumbers(arrayOf(4, 6, 5, 3, 3, 1)))
        val ints = Resources.resourceAsList("pickingNumbers.txt").joinToString().split(" ").map { it.toInt() }
        assertEquals(22, pickingNumbers(ints.toTypedArray()))
    }
}

