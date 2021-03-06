package marcinmoskala

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * https://blog.kotlin-academy.com/
 */

class KotlinAcademyTest {

    @Test
    fun group_by_is_the_opposite_of_flatmap() {
        val names = listOf("John", "Simon", "Andrew", "Peter")

        assertEquals(names, names.groupBy { it.first() }.flatMap { it.value })
    }

    @Test
    fun multiple_ways_of_doing_fibonacci() {
        fun fibRecursive(n: Int): Int = if (n <= 2) 1 else fibRecursive(n - 1) + fibRecursive(n - 2)

        fun fibIterative(n: Int): Int {
            var a = 1
            var b = 1
            for (i in 3..n) {
                val tmp = a
                a += b
                b = tmp
            }
            return a
        }

        tailrec fun fibTailRec(n: Int, prev: Int, next: Int): Int =
            when (n) {
                0 -> prev
                1 -> next
                else -> fibTailRec(n - 1, next, next + prev)
            }

        fun fibTailRecStart(n: Int) = fibTailRec(n, 0, 1)

        fun fibFunctional(n: Int) = (2 until n).fold(1 to 1) { (f, s), _ -> s to (f + s) }.second

        val testData = mapOf(
            10 to 55,
            20 to 6765,
            30 to 832040,
            40 to 102334155
        )

        testData.forEach { (n, result) ->
            assertEquals(result, fibRecursive(n))
            assertEquals(result, fibIterative(n))
            assertEquals(result, fibTailRecStart(n))
            assertEquals(result, fibFunctional(n))
        }
    }

    @Test
    fun fold_is_the_mother_of_collection_processing_functions() {
        fun Iterable<Int>.sum(): Int = fold(0) { acc, i -> acc + i }
        fun Iterable<Int>.product(): Int = fold(1) { acc, i -> acc * i }
        fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> = fold(emptyList()) { acc, i -> acc + transform(i) }
        fun <T, R> Iterable<T>.flatMap(transform: (T) -> List<R>): List<R> =
            fold(emptyList()) { acc, i -> acc + transform(i) }

        fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> =
            fold(emptyList()) { acc, i -> if (predicate(i)) acc + (i) else acc }

        fun <T> Iterable<T>.joinToString(separator: String = " ,", tr: (T) -> String = { "$it" }): String =
            foldIndexed("") { index, acc, i -> acc + (if (index != 0) separator else "") + tr(i) }

        val list = listOf(1, 2, 3, 4, 5)

        assertEquals(15, list.sum())
        assertEquals(120, list.product())
        assertEquals(listOf(2, 4, 6, 8, 10), list.map { it * 2 })
        assertEquals(listOf(2, 4), list.filter { it % 2 == 0 })
        assertEquals("12345", list.joinToString(separator = ""))
        assertEquals(listOf(1, 11, 2, 12, 3, 13, 4, 14, 5, 15), list.flatMap { listOf(it, it + 10) })
    }
}