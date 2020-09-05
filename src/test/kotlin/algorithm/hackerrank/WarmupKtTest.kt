package algorithm.hackerrank

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class WarmupKtTest {

    @Test
    fun diagonalDifference() {
        assertEquals(2, diagonalDifference(arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(9, 8, 9))))
    }

    @Test
    fun compareTriplets() {
        assertArrayEquals(arrayOf(1, 1), compareTriplets(arrayOf(5, 6, 7), arrayOf(3, 6, 10)))
        assertArrayEquals(arrayOf(2, 1), compareTriplets(arrayOf(17, 28, 30), arrayOf(99, 16, 8)))
    }

    @Test
    fun aVeryBigSum() {
        assertEquals(5000000015L, aVeryBigSum(arrayOf(1000000001, 1000000002, 1000000003, 1000000004, 1000000005)))
    }

    @Test
    fun plusMinus() {
        plusMinus(arrayOf(-4, 3, -9, 0, 4, 1))
    }

    @Test
    fun staircase() {
        staircase(5)
    }
}