package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import kotlin.sequences.drop
import kotlin.sequences.filter
import kotlin.sequences.first
import kotlin.sequences.flatten
import kotlin.sequences.last
import kotlin.sequences.map
import kotlin.sequences.plus
import kotlin.sequences.sequenceOf
import kotlin.sequences.sum
import kotlin.sequences.take
import kotlin.sequences.toList


private data class Ranges(val name: String, val low: Range, val high: Range)
private data class Range(val from: Int, val until: Int)
private data class Ticket(
    val ranges: List<Ranges>,
    val yourTicket: List<Int>,
    val nearbyTickets: Iterable<IndexedValue<List<Int>>>
)

class Day16Test {

    private val input by lazy {
        val nestedLists = Resources.resourceAsList("adventofcode/2020/Day16.txt")
            .asSequence()
            .chunked { last, _ -> last.isEmpty() }
            .map { it.filterNot { it.isEmpty() } }

        val ranges = nestedLists.first()
            .map {
                val split = it.split(":")
                split.first() to split.drop(1).flatMap { it.split("or") }
            }
            .map { (name, list) ->
                val rangesList = list.flatMap { it.split("-") }.map { it.trim() }.map { it.toInt() }
                Ranges(name, Range(rangesList[0], rangesList[1]), Range(rangesList[2], rangesList[3]))
            }

        val yourTicket = nestedLists.drop(1).take(1).first().drop(1).flatMap { it.trim().split(",").map { it.toInt() } }
        val nearbyTickets = nestedLists.last().drop(1).map {
            it.trim().split(",").map { it.toInt() }
        }.withIndex()

        Ticket(ranges, yourTicket, nearbyTickets)
    }

    @Test
    fun `part 1`() {
        assertEquals(22977, input.findInvalid().map { it.second }.sum())
    }

    @Ignore
    fun `part 2 not working`() {
        val invalidIndexes = input.findInvalid().map { it.first }
        val validTicket =
            input.copy(nearbyTickets = input.nearbyTickets.filterIndexed { index, _ -> index !in invalidIndexes })

        val availableRanges = validTicket.ranges
        val zippedList = aggregatePositions(validTicket.nearbyTickets.map { it.value })

        println((createRanges(validTicket.nearbyTickets.map { it.value }, availableRanges).filter { (_, rangeName) ->
            rangeName.startsWith("departure")
        }.multiplyBy { (ticketNumber, _) -> ticketNumber }))
    }

    private fun List<Int>.findRange(ranges: List<Ranges>) =
        ranges.find { aRanges -> all { it in aRanges } } ?: throw IllegalStateException()

    private operator fun Ranges.contains(number: Int) =
        number in low.from..low.until || number in high.from..high.until

    private fun Ticket.findInvalid() = nearbyTickets.flatMap { it.value }.mapNotNull { ticket ->
        if (ranges.none { ranges -> (ticket in ranges.low.from..ranges.low.until || ticket in ranges.high.from..ranges.high.until) }) ticket
        else null
    }.map { invalidTicketNumber ->
        nearbyTickets.first { it.value.any { it == invalidTicketNumber } }.index to invalidTicketNumber
    }

    private fun createRanges(lists: List<List<Int>>, availableRanges: List<Ranges>): List<Pair<Int, String>> {
        var matches: MutableMap<Pair<Int, List<Int>>, List<Ranges>> = mutableMapOf()
        repeat(lists.first().size) { index ->
            val list = lists.map { it[index] }
            availableRanges.forEach { ranges ->
                println("@@@ not in range: " + list.filterIndexed { index1, i ->
                    i !in ranges.also {
                        if (i !in ranges) {
                            println("Index: $index1")
                        }
                    }
                })
                if (list.all { it in ranges }) {
                    matches[index to list] =
                        matches[index to list]?.let { set -> set + listOf(ranges) } ?: listOf(ranges)
                }
            }
        }

        println("@@@ matches: ${matches.map { it.key.first to it.value.map { it.name } to it.value.size }}")
        println("@@@ matches.size: " + matches.size + " lists.size: " + lists.size)

        val result: MutableMap<List<Int>, List<Ranges>> = mutableMapOf()
        while (result.size < lists.size) {
            matches.filter { it.value.size == 1 }.map {
                result[it.key.second] = it.value
            }
            matches = matches.mapValues {
                it.value - result.values.flatten()
            }.toMutableMap()
        }
        return result.map { it.key.first() to it.value.first().name }
    }

    private inline fun <T> Iterable<T>.multiplyBy(selector: (T) -> Int): Int {
        var multiplication = 1
        for (element in this) {
            multiplication *= selector(element)
        }
        return multiplication
    }

    private tailrec fun aggregatePositions(
        lists: List<List<Int>>,
        result: List<List<Int>> = emptyList()
    ): List<List<Int>> =
        if (lists.any { it.isEmpty() }) result
        else aggregatePositions(lists.map { it.drop(1) }, result + listOf(lists.flatMap { it.take(1) }))

    private data class Restriction(val name: String, val low: IntRange, val high: IntRange)

    private data class InputData(
        val restrictions: List<Restriction>,
        val myTicket: List<Int>,
        val nearbyTickets: List<List<Int>>
    )

    private fun getInput(): InputData {
        val data = Filename("adventofcode/2020/Day16.txt").readChunkedLines()

        val restrictions = data[0].map { line ->
            val (name, rest) = line.split(": ")
            val (low, high) = rest.split(" or ").map {
                val (lower, upper) = it.split("-")
                IntRange(lower.toInt(), upper.toInt())
            }
            Restriction(name, low, high)
        }

        val myTicket = data[1][1].split(",").map { it.toInt() }
        val nearbyTickets = data[2].subList(1, data[2].size).map { line ->
            line.split(",").map { it.toInt() }
        }
        return InputData(restrictions, myTicket, nearbyTickets)
    }

    @Test
    fun `part 1 Nir solution`() {
        val inputData = getInput()
        assertEquals(22977, inputData.nearbyTickets.asSequence().flatten().filter { ticketValue ->
            inputData.restrictions.none { (_, low, high) -> ticketValue in low || ticketValue in high }
        }.sum())
    }

    @Test
    fun `part 2 Nir solution`() {
        val inputData = getInput()
        val validTickets = (inputData.nearbyTickets.asSequence().filter { ticket ->
            ticket.all { ticketValue ->
                inputData.restrictions.any { (_, low, high) ->
                    ticketValue in low || ticketValue in high
                }
            }
        } + sequenceOf(inputData.myTicket)).toList()

        val numFields = inputData.myTicket.size
        val possibilities = (1..numFields).map { (0 until numFields).toMutableSet() }

        for (ticket in validTickets) {
            for ((ticketIndex, ticketValue) in ticket.withIndex()) {
                for (restrictionIndex in possibilities[ticketIndex].toList()) {
                    val restriction = inputData.restrictions[restrictionIndex]
                    if (ticketValue !in restriction.low && ticketValue !in restriction.high) {
                        possibilities[ticketIndex].remove(restrictionIndex)
                    }
                }
            }
        }
        while (true) {
            val determined = possibilities.filter { it.size == 1 }
            if (determined.size == possibilities.size) break
            for (d in determined) {
                for (p in possibilities) {
                    if (!(d === p)) p.remove(d.first())
                }
            }
        }
        val timesFields = (0 until numFields).filter { ticketIndex ->
            inputData.restrictions[possibilities[ticketIndex].first()].name.startsWith("departure")
        }.map { inputData.myTicket[it] }
        assert(timesFields.size == 6)

        assertEquals(998358379943, (timesFields.fold(1L) { acc, i -> acc * i.toLong() }))
    }
}