package algorithm.hackerrank

import org.junit.Assert.assertEquals
import org.junit.Test

class StringsKtTest {

    @Test
    fun superReducedString() {
        assertEquals("abd", superReducedString("aaabccddd"))
        assertEquals("abda", superReducedString("aaabccdddaaa"))
        assertEquals("Empty String", superReducedString("aa"))
        assertEquals("Empty String", superReducedString("baab"))
    }
}