package algorithm.hackerrank

import org.junit.Assert.assertEquals
import org.junit.Test

class WarmUpChallengesKtTest {

    @Test
    fun `sock merchant`() {
        assertEquals(4, sockMerchant(3, arrayOf(1, 1, 3, 1, 2, 1, 3, 3, 3, 3)))
    }

    @Test
    fun `counting valleys`() {
        assertEquals(1, countingValleys(2, "UDDDUDUU"))
    }

    @Test
    fun `jumping on clouds`() {
        assertEquals(4, jumpingOnClouds(arrayOf(0, 0, 1, 0, 0, 1, 0)))
        assertEquals(3, jumpingOnClouds(arrayOf(0, 0, 0, 0, 1, 0)))
    }

    @Test
    fun `repeated strings`() {
        assertEquals(7, repeatedString("aba", 10))
        assertEquals(1000000000000, repeatedString("a", 1000000000000))
    }
}