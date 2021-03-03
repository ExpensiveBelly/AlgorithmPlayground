package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.collections.map
import kotlin.collections.sum
import kotlin.sequences.drop
import kotlin.sequences.first
import kotlin.sequences.last
import kotlin.sequences.map
import kotlin.sequences.take


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
        val nearbyTickets = nestedLists.last().drop(1).map { it.trim().split(",").map { it.toInt() } }.withIndex()

        Ticket(ranges, yourTicket, nearbyTickets)
    }

    @Test
    fun `part 1`() {
        assertEquals(22977, input.findInvalid().map { it.second }.sum())
    }

    @Test
    fun `part 2`() {
        val invalidIndexes = input.findInvalid().map { it.first }
        val validTicket =
            input.copy(nearbyTickets = input.nearbyTickets.filterIndexed { index, _ -> index !in invalidIndexes })

        var availableRanges = validTicket.ranges
        println(validTicket.yourTicket.zip(aggregatePositions((validTicket.nearbyTickets.map { it.value } + listOf(
            validTicket.yourTicket
        ))).map { it.findRange(availableRanges).also { availableRanges -= it } }.map { it.name })
            .filter { (_, rangeName) ->
                rangeName.startsWith("departure")
            }.multiplyBy { (ticketNumber, _) -> ticketNumber })

        (input.yourTicket + input.nearbyTickets.flatMap { it.value })
    }

    private tailrec fun aggregatePositions(
        lists: List<List<Int>>,
        result: List<List<Int>> = emptyList()
    ): List<List<Int>> =
        if (lists.any { it.isEmpty() }) result
        else aggregatePositions(lists.map { it.drop(1) }, result + listOf(lists.map { it.take(1) }.flatten()))
}

private inline fun <T> Iterable<T>.multiplyBy(selector: (T) -> Int): Int {
    var multiplication = 1
    for (element in this) {
        multiplication *= selector(element)
    }
    return multiplication
}

private fun List<Int>.findRange(ranges: List<Ranges>) =
    ranges.find { aRanges -> all { it in aRanges } } ?: throw IllegalStateException()

private operator fun Ranges.contains(number: Int) =
    number in low.from..low.until || number in high.from..high.until

private fun Ticket.findInvalid() = nearbyTickets.map { it.value }.flatten().mapNotNull { ticket ->
    if (ranges.none { ranges -> (ticket in ranges.low.from..ranges.low.until || ticket in ranges.high.from..ranges.high.until) }) ticket
    else null
}.map { invalidTicketNumber ->
    nearbyTickets.first { it.value.any { it == invalidTicketNumber } }.index to invalidTicketNumber
}
