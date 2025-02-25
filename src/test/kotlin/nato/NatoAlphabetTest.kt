package nato

import Resources
import org.junit.Test
import java.util.Locale

class NatoAlphabetTest {

    @Test
    fun `show nato alphabet list`() {
        val natoAlphabetList = Resources.resourceAsList("natoAlphabet.txt")

        natoAlphabetList.forEach { println(it) }
    }

    @Test
    fun `show nato alphabet list for a given word`() {
        val natoAlphabetMap = Resources.resourceAsList("natoAlphabet.txt").groupBy { it.lowercase(
            Locale.getDefault()
        )
            .first() }

        val words = "My name"

        words.lowercase(Locale.getDefault()).forEach { println(natoAlphabetMap[it] ?: " ") }
    }
}