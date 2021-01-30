package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.absoluteValue


class Day12Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day12.txt").map { ShipInstruction(it[0], it.drop(1).toInt()) }
    }

    @ExperimentalStdlibApi
    @Test
    fun `part 1`() {
        val (shipDirection, _) = input.scan(
            ShipDirection(
                north = 0,
                east = 0,
                south = 0,
                west = 0
            ) to CurrentDirection.EAST
        ) { (shipDirection, currentDirection): Pair<ShipDirection, CurrentDirection>, shipInstruction: ShipInstruction ->
            when (shipInstruction.action) {
                'N' -> shipDirection.copy(north = shipDirection.north + shipInstruction.units) to currentDirection
                'S' -> shipDirection.copy(south = shipDirection.south + shipInstruction.units) to currentDirection
                'E' -> shipDirection.copy(east = shipDirection.east + shipInstruction.units) to currentDirection
                'W' -> shipDirection.copy(west = shipDirection.west + shipInstruction.units) to currentDirection
                'F' -> when (currentDirection) {
                    CurrentDirection.NORTH -> shipDirection.copy(north = shipDirection.north + shipInstruction.units) to currentDirection
                    CurrentDirection.EAST -> shipDirection.copy(east = shipDirection.east + shipInstruction.units) to currentDirection
                    CurrentDirection.SOUTH -> shipDirection.copy(south = shipDirection.south + shipInstruction.units) to currentDirection
                    CurrentDirection.WEST -> shipDirection.copy(west = shipDirection.west + shipInstruction.units) to currentDirection
                }
                'L' -> shipDirection to (currentDirection + ((360 - shipInstruction.units) / 90))
                'R' -> shipDirection to (currentDirection + (shipInstruction.units / 90))
                else -> throw IllegalStateException()
            }
        }.last()
        val manhattan = shipDirection.let {
            (it.east - it.west).absoluteValue + (it.north - it.south).absoluteValue
        }
        assertEquals(1221, manhattan)
    }
}

private operator fun CurrentDirection.plus(that: Int) =
    CurrentDirection.values()[(value + that).rem(CurrentDirection.values().size)]

private enum class CurrentDirection(val value: Int) {
    EAST(0), SOUTH(1), WEST(2), NORTH(3)
}

private data class ShipInstruction(val action: Char, val units: Int)

private data class ShipDirection(
    val north: Int = 0,
    val east: Int = 0,
    val south: Int = 0,
    val west: Int = 0
)