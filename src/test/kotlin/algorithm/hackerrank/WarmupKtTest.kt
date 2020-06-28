package algorithm.hackerrank

import org.junit.Assert.assertEquals
import org.junit.Test

class WarmupKtTest {

    @Test
    fun diagonalDifference() {
        assertEquals(2, diagonalDifference(arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(9, 8, 9))))
    }
}