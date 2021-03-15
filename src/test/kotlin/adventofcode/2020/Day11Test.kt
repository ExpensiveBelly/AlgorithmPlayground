package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

private const val OCCUPIED = '#'
private const val EMPTY = 'L'

class Day11Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day11.txt").map { it.toList() }
    }

    @Test
    fun `part 1`() {
        var a = input
        var b = nextRound(a)
        while (b != a) {
            a = b
            b = nextRound(a)
        }
//        println(a.map { it.joinToString(" ") + '\n' })
        assertEquals(2238, a.sumBy { row -> row.count { it.isOccupied() } })
    }

    @Test
    fun `part 2`() {
        var a = input
        var b = nextRoundPart2(a)

        while (b != a) {
            a = b
            b = nextRoundPart2(a)
        }

        assertEquals(2013, a.sumBy { row -> row.count { it.isOccupied() } })
    }

    @Test
    fun `first seats for the example`() {
        val example = Resources.resourceAsList("adventofcode/2020/Day11Example.txt").map { it.toList() }

        assertEquals(8, example.firstSeats(i = 4, j = 3).count { it.isOccupied() })
    }
}

private fun Char.isOccupied(): Boolean = equals(OCCUPIED)
private fun Char.isEmpty() = equals(EMPTY)

private fun List<List<Char>>.adjacents(i: Int, j: Int) =
    neighborCoordinatesOf(i, j).filter { it.isInBounds(size, this[0].size) }
        .map { (x, y) -> this[x][y] }

fun Pair<Int, Int>.isInBounds(rowSize: Int, columnSize: Int) =
    !(first < 0).or(first >= rowSize).or(second < 0).or(second >= columnSize)

private fun neighborCoordinatesOf(x: Int, y: Int) = arrayOf(
    Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1), Pair(x - 1, y),
    Pair(x + 1, y), Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
)

private fun nextRoundPart2(seatPlan: List<List<Char>>) =
    seatPlan.mapIndexed { i, row ->
        row.mapIndexed { j, seat ->
            when {
                seat.isEmpty() && seatPlan.firstSeats(i, j).none { it.isOccupied() } -> OCCUPIED
                seat.isOccupied() && seatPlan.firstSeats(i, j).filter { it.isOccupied() }.size >= 5 -> EMPTY
                else -> seat
            }
        }
    }

fun List<List<Char>>.firstSeats(i: Int, j: Int, debug: Boolean = false): List<Char> =
    listOfNotNull(
        (j - 1 downTo 0).map { this[i][it] }.firstOrNull { it.isOccupied() || it.isEmpty() }
            .also { if (debug) println("1 $it") }, //Left till j
        (j + 1 until this[0].size).map { this[i][it] }.firstOrNull { it.isOccupied() || it.isEmpty() }
            .also { if (debug) println("2 $it") }, //j till end
        (i - 1 downTo 0).map { this[it][j] }.firstOrNull { it.isOccupied() || it.isEmpty() }
            .also { if (debug) println("3 $it") }, //Top till i
        (i + 1 until size).map { this[it][j] }.firstOrNull { it.isOccupied() || it.isEmpty() }
            .also { if (debug) println("4 $it") }, //i till bottom
        (i - 1 downTo 0).zip(j - 1 downTo 0).filter { it.isInBounds(size, get(0).size) }.map { (x, y) -> this[x][y] }
            .firstOrNull { it.isOccupied() || it.isEmpty() }.also { if (debug) println("5 $it") }, //top-left diagonal
        (i + 1 until size).zip(j + 1 until this[0].size).filter { (x, y) -> x < size && y < this[0].size }
            .map { (x, y) -> this[x][y] }
            .firstOrNull { it.isOccupied() || it.isEmpty() }.also { if (debug) println("6 $it") },
        (i - 1 downTo 0).zip(j + 1 until this[0].size).filter { (x, y) -> x >= 0 && y < this[0].size }
            .map { (x, y) -> this[x][y] }
            .firstOrNull { it.isOccupied() || it.isEmpty() }.also { if (debug) println("7 $it") },
        (i + 1 until size).zip(j - 1 downTo 0).filter { (x, y) -> x < size && y >= 0 }
            .map { (x, y) -> this[x][y] }
            .firstOrNull { it.isOccupied() || it.isEmpty() }.also { if (debug) println("8 $it") }
    )

private fun nextRound(seatPlan: List<List<Char>>) =
    seatPlan.mapIndexed { i, row ->
        row.mapIndexed { j, seat ->
            when {
                seat.isEmpty() && seatPlan.adjacents(i, j).none { it.isOccupied() } -> OCCUPIED
                seat.isOccupied() && seatPlan.adjacents(i, j).filter { it.isOccupied() }.size >= 4 -> EMPTY
                else -> seat
            }
        }
    }


