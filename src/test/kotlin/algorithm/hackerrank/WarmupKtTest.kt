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
}