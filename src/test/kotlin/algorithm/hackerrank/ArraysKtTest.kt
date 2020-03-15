package algorithm.hackerrank

import org.junit.Assert.assertEquals
import org.junit.Test

class ArraysKtTest {

    @Test
    fun hourglass() {
        assertEquals(
            19, hourglassSum(
                arrayOf(
                    arrayOf(1, 1, 1, 0, 0, 0),
                    arrayOf(0, 1, 0, 0, 0, 0),
                    arrayOf(1, 1, 1, 0, 0, 0),
                    arrayOf(0, 9, 2, -4 - 4, 0),
                    arrayOf(0, 0, 0, -2, 0, 0),
                    arrayOf(0, 0, -1, -2, -4, 0)
                )
            )
        )
        assertEquals(
            56,
            arrayOf(
                arrayOf(1, 1, 1, 0, 0, 0),
                arrayOf(0, 1, 0, 0, 0, 0),
                arrayOf(1, 1, 1, 0, 0, 0),
                arrayOf(0, 0, 2, 4, 4, 0),
                arrayOf(0, 0, 0, 2, 0, 0),
                arrayOf(0, 0, 1, 2, 4, 0)
            )
        )
    }
}