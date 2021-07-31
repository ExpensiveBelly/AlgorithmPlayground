package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test


class Day13Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day13.txt")
    }

    @Test
    fun `part 1`() {
        val timestamp = input.first().toInt()
        val buses =
            input.last().split(",")
                .filterNot { it.equals("x", ignoreCase = true) }
                .map { it.toInt() }
                .sorted()

        val busTimes = buses.map { bus ->
            bus to generateSequence(bus) { it + bus }.takeWhile { it < timestamp + bus }.last()
        }.toMap()

        val entry = busTimes.minByOrNull { it.value }!!
        assertEquals(102, entry.key * entry.value.minus(timestamp))
    }

    @Test
    fun `part 2`() {
        val busesWithOffsets =
            input.last().split(",").withIndex().filter { it.value != "x" }
                .map { it.value.toLong() to it.index.toLong() }
//        val debug = true
//        var multiple = 1L
//        var result = 0L
//        if (debug) println(busesWithOffsets)
//        busesWithOffsets.forEach { (bus, offset) ->
//            while (((result + offset) % bus != 0L).also { if (debug) println("($result + $offset) % $bus") }) {
//                if (debug) println("result = $result + $multiple")
//                result += multiple
//            }
//            if (debug) println("multiple = $multiple * $bus")
//            multiple *= bus
//        }
//        println(result.toString())

        assertEquals(327300950120029, findTimeStamp(busesWithOffsets))
    }

    private tailrec fun findTimeStamp(buses: List<Pair<Long, Long>>, multiple: Long = 1, result: Long = 0): Long =
        if (buses.isEmpty()) result
        else {
            val (bus, offset) = buses.first()
            findTimeStamp(buses.drop(1), multiple * bus, calculateResult(result, multiple, bus, offset))
        }

    private tailrec fun calculateResult(result: Long, multiple: Long, bus: Long, offset: Long): Long =
        if ((result + offset) % bus != 0L) calculateResult(result + multiple, multiple, bus, offset)
        else result
}