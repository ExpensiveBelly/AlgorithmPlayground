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

    }
}

private fun nextRound(seatPlan: List<List<Char>>) =
    seatPlan.mapIndexed { i, row ->
        row.mapIndexed { j, seat ->
            when {
                seat.isEmpty() && adjacents(seatPlan, j, i).none { it.isOccupied() } -> OCCUPIED
                seat.isOccupied() && adjacents(seatPlan, j, i).filter { it.isOccupied() }.size >= 4 -> EMPTY
                else -> seat
            }
        }
    }

private fun Char.isOccupied(): Boolean = equals(OCCUPIED)
private fun Char.isEmpty() = equals(EMPTY)

private fun adjacents(seatPlan: List<List<Char>>, j: Int, i: Int) =
    neighborCoordinatesOf(i, j).filter { it.isInBounds(seatPlan.size, seatPlan[0].size) }
        .map { (x, y) -> seatPlan[x][y] }

private fun Pair<Int, Int>.isInBounds(rowSize: Int, columnSize: Int) =
    !(first < 0).or(first >= rowSize).or(second < 0).or(second >= columnSize)

private fun neighborCoordinatesOf(x: Int, y: Int) = arrayOf(
    Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1), Pair(x - 1, y),
    Pair(x + 1, y), Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
)