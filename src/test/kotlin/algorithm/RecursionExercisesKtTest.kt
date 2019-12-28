package algorithm

import junit.framework.TestCase.assertEquals
import org.junit.Test
import recursion.RecursionExercises

class RecursionExercisesKtTest {

    private val recursionExercises = RecursionExercises()

    @Test
    fun toBinary() {
        assertEquals("1100", recursionExercises.toBinary(12))
    }
}