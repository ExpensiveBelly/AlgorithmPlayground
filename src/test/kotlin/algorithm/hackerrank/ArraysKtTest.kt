package algorithm.hackerrank

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertArrayEquals
import org.junit.Test

class ArraysKtTest {

    @Test
    fun hourglass() {
        assertEquals(
            13, hourglassSum(
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
            19,
            hourglassSum(
                arrayOf(
                    arrayOf(1, 1, 1, 0, 0, 0),
                    arrayOf(0, 1, 0, 0, 0, 0),
                    arrayOf(1, 1, 1, 0, 0, 0),
                    arrayOf(0, 0, 2, 4, 4, 0),
                    arrayOf(0, 0, 0, 2, 0, 0),
                    arrayOf(0, 0, 1, 2, 4, 0)
                )
            )
        )
    }

    @Test
    fun `rotate left`() {
        assertArrayEquals(arrayOf(5, 1, 2, 3, 4), rotLeft(arrayOf(1, 2, 3, 4, 5), 4))
    }

    @Test
    fun `minimum bribe`() {
        minimumBribes(arrayOf(2, 1, 5, 3, 4))
        minimumBribes(arrayOf(2, 5, 1, 3, 4))
//        1, 2, 3, 4, 5, 6, 7, 8
//        3, 1, 2, 4, 5, 6, 7, 8
//        2, 1, 3, 4, 5, 6, 7, 8

        minimumBribes(arrayOf(1, 2, 5, 3, 7, 8, 6, 4))
    }

    @Test
    fun minimumSwaps() {
        assertEquals(5, minimumSwaps(arrayOf(7, 1, 3, 2, 4, 5, 6)))
        assertEquals(3, minimumSwaps(arrayOf(4, 3, 1, 2)))
        assertEquals(3, minimumSwaps(arrayOf(2, 3, 4, 1, 5)))
        assertEquals(3, minimumSwaps(arrayOf(1, 3, 5, 2, 4, 6, 7)))
    }
}