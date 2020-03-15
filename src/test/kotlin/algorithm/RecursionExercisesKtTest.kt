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

    @Test
    fun flatten() {
        assertEquals(
            (1..13).toList(), recursionExercises.flattenRecTail(
                listOf(1, 2, listOf(3, 4, listOf(5, 6, listOf(7, 8, listOf(9, 10, 11)))), 12, 13)
            )
        )
    }
}