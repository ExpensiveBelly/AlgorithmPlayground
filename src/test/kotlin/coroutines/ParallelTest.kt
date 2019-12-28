package coroutines

import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.system.measureNanoTime

class ParallelTest {

    private val n = 100

    @Test
    fun `should paralellise map operation`() {
        val list = generateSequence(0) { it + 1 }.take(n).toList()

        val parallelTime = measureNanoTime {
            list.pmap { compute(it) }
        }
        val sequentialTime = measureNanoTime {
            list.map { compute(it) }
        }

        println("parallelTimeMillis: $parallelTime")
        println("sequentialTimeMillis: $sequentialTime")

        assertTrue(parallelTime < sequentialTime)
    }

    private fun compute(i: Int): Int {
        Thread.sleep(100)
        return i
    }

    @Test
    fun `should paralellise for loop operation`() {
        val list = generateSequence(0) { it + 1 }.take(n).toList()

        val parallelTime = measureNanoTime {
            list.forEachParallel { compute(it) }
        }
        val sequentialTime = measureNanoTime {
            list.forEach { compute(it) }
        }

        println("parallelTimeMillis: $parallelTime")
        println("sequentialTimeMillis: $sequentialTime")

        assertTrue(parallelTime < sequentialTime)
    }
}