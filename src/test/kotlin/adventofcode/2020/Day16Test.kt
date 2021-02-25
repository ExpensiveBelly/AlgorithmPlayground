package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test


private data class Range(val from: Int, val until: Int)
private data class Ticket(val ranges: List<Range>, val yourTicket: List<String>, val nearbyTickets: List<List<Int>>)

class Day16Test {

    private val input by lazy {
        val nestedLists = Resources.resourceAsList("adventofcode/2020/Day16.txt")
                .asSequence()
                .chunked { last, _ -> last.isEmpty() }
                .map { it.filterNot { it.isEmpty() } }
                .toList()
        nestedLists.first().map { it.split(":") }
    }

    @Test
    fun `part 1`() {
        val nestedLists = Resources.resourceAsList("adventofcode/2020/Day16.txt")
                .asSequence()
                .chunked { last, _ -> last.isEmpty() }
                .map { it.filterNot { it.isEmpty() } }

        val ranges =
                nestedLists.first()
                        .map { it.split(":") }
                        .map { it.drop(1) }
                        .flatMap { it.map { it.split("or") } }
                        .flatMap { it.map { it.split("-").map { it.trim() }.map { it.toInt() } } }
                        .map { Range(it.first(), it.last()) }

        val yourTicket =
                nestedLists.drop(1).take(1).first().drop(1)

        val nearbyTickets = nestedLists.last().drop(1).map { it.trim().split(",").map { it.toInt() } }

        assertEquals(22977, Ticket(ranges, yourTicket, nearbyTickets).findInvalid().sum())
    }
}

private fun Ticket.findInvalid() = nearbyTickets.flatten().mapNotNull { ticket ->
    if (ranges.none { range -> (ticket in range.from..range.until) }) ticket
    else null
}
