package algorithm.hackerrank

import Resources
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
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

    @Test
    fun `climbing leaderboard`() {
        assertArrayEquals(
            arrayOf(6, 4, 2, 1),
            climbingLeaderboard(arrayOf(100, 100, 50, 40, 40, 20, 10), arrayOf(5, 25, 50, 120))
        )
        assertArrayEquals(
            arrayOf(6, 5, 4, 2, 1),
            climbingLeaderboard(arrayOf(100, 90, 90, 80, 75, 60), arrayOf(50, 65, 77, 90, 102))
        )
    }

    @Test
    fun `permutation equation`() {
        assertArrayEquals(
            arrayOf(2, 3, 1),
            permutationEquation(arrayOf(2, 3, 1))
        )

        assertArrayEquals(
            arrayOf(1, 3, 5, 4, 2),
            permutationEquation(arrayOf(4, 3, 5, 1, 2))
        )
    }

    @Test
    fun `append and delete`() {
        assertEquals("Yes", appendAndDelete("hackerhappy", "hackerrank", 9))
        assertEquals("Yes", appendAndDelete("hackerhappy", "hacker", 9))
        assertEquals("Yes", appendAndDelete("aba", "aba", 7))
        assertEquals("No", appendAndDelete("ashley", "ash", 2))
        assertEquals("Yes", appendAndDelete("aaaaaaaaaa", "aaaaa", 7))
        assertEquals("No", appendAndDelete("y", "yu", 2))
        assertEquals("No", appendAndDelete("abc", "abc", 1))
        assertEquals("No", appendAndDelete("peek", "seeker", 3))
        assertEquals(
            "No", appendAndDelete(
                "asdfqwertyuighjkzxcvasdfqwertyuighjkzxcvasdfqwertyuighjkzxcvasdfqwertyuighjkzxcvasdfqwertyuighjkzxcv",
                "bsdfqwertyuighjkzxcvasdfqwertyuighjkzxcvasdfqwertyuighjkzxcvasdfqwertyuighjkzxcvasdfqwertyuighjkzxcv",
                100
            )
        )
    }

    @Test
    fun gradingStudents() {
        assertArrayEquals(arrayOf(75, 67, 40, 33), gradingStudents(arrayOf(73, 67, 38, 33)))
    }
}

