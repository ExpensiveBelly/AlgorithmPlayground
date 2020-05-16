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

    @Test
    fun separateNumbers() {
        assertEquals("YES 1", separateNumbers("1234"))
        assertEquals("YES 9", separateNumbers("91011"))
        assertEquals("YES 99", separateNumbers("99100"))
        assertEquals("YES 999", separateNumbers("99910001001"))
        assertEquals("NO", separateNumbers("9991000100"))
        assertEquals("NO", separateNumbers("101103"))
        assertEquals("NO", separateNumbers("010203"))
        assertEquals("NO", separateNumbers("13"))
        assertEquals("NO", separateNumbers("1"))

        assertEquals("YES 4294967295", separateNumbers("429496729542949672964294967297"))
        assertEquals("NO", separateNumbers("429496729542949672964294967296"))
        assertEquals("NO", separateNumbers("429496729542949672964294967287"))
        assertEquals("NO", separateNumbers("429496729542949672964294967197"))
        assertEquals("NO", separateNumbers("42949672954294967296429496729"))
        assertEquals("NO", separateNumbers("4294967295429496729642949672"))
        assertEquals("NO", separateNumbers("429496729500000000000000000001"))
        assertEquals("NO", separateNumbers("42949672950123456789"))
        assertEquals("NO", separateNumbers("4294967295000010020030000456789"))
        assertEquals("NO", separateNumbers("4294967295000102003004005"))

        assertEquals("YES 85", separateNumbers("858687888990919293949596979899"))
        assertEquals("NO", separateNumbers("858687888990919293949596979898"))
        assertEquals("YES 65", separateNumbers("65666768697071727374757677787980"))
        assertEquals("NO", separateNumbers("65666768697071727374757677787970"))
        assertEquals("YES 24", separateNumbers("24252627282930313233343536373839"))
        assertEquals("NO", separateNumbers("24252627282930313233343536373739"))
        assertEquals("YES 78", separateNumbers("78798081828384858687888990919293"))
        assertEquals("NO", separateNumbers("78798081828384858687888990918293"))
        assertEquals("YES 95", separateNumbers("9596979899100101102103104105106"))
        assertEquals("NO", separateNumbers("9596979899100101102103104195106 "))
    }
}