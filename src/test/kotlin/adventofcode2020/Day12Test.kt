package adventofcode2020

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.absoluteValue

class Day12Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day12.txt").map { ShipInstruction(it[0], it.drop(1).toInt()) }
    }

    @Test
    fun `part 1`() {
        val (shipDirection, _) = input.fold(
            Direction(
                north = 0,
                east = 0,
                south = 0,
                west = 0
            ) to CurrentDirection.EAST
        ) { (shipDirection, currentDirection): Pair<Direction, CurrentDirection>, shipInstruction: ShipInstruction ->
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
        }
        val manhattan = shipDirection.let {
            (it.east - it.west).absoluteValue + (it.north - it.south).absoluteValue
        }
        assertEquals(1221, manhattan)
    }

    @Test
    fun `part 2`() {
        val (ship, _) = input.fold(
            Direction() to Direction(east = 10, north = 1),
            { (ship, wayPoint), shipInstruction ->
                when (shipInstruction.action) {
                    'N' -> ship to wayPoint.copy(north = wayPoint.north + shipInstruction.units)
                    'S' -> ship to wayPoint.copy(south = wayPoint.south + shipInstruction.units)
                    'E' -> ship to wayPoint.copy(east = wayPoint.east + shipInstruction.units)
                    'W' -> ship to wayPoint.copy(west = wayPoint.west + shipInstruction.units)
                    'F' -> ship + (wayPoint * shipInstruction.units) to wayPoint
                    'L' -> ship to wayPoint.rotate(((360 - shipInstruction.units) / 90))
                    'R' -> ship to wayPoint.rotate(shipInstruction.units / 90)
                    else -> throw IllegalStateException()
                }
            })
        val manhattan = ship.let {
            (it.east - it.west).absoluteValue + (it.north - it.south).absoluteValue
        }
        assertEquals(59435, manhattan)
    }
}

private fun Direction.rotate(degrees: Int): Direction {
    val directionNorthRotated = when ((CurrentDirection.NORTH + degrees)) {
        CurrentDirection.EAST -> Direction(north = 0, east = north)
        CurrentDirection.SOUTH -> Direction(north = 0, south = north)
        CurrentDirection.WEST -> Direction(north = 0, west = north)
        CurrentDirection.NORTH -> throw IllegalStateException()
    }

    val directionSouthRotated = when ((CurrentDirection.SOUTH + degrees)) {
        CurrentDirection.EAST -> Direction(south = 0, east = south)
        CurrentDirection.SOUTH -> throw IllegalStateException()
        CurrentDirection.WEST -> Direction(south = 0, west = south)
        CurrentDirection.NORTH -> Direction(south = 0, north = south)
    }

    val directionEastRotated = when ((CurrentDirection.EAST + degrees)) {
        CurrentDirection.EAST -> throw IllegalStateException()
        CurrentDirection.SOUTH -> Direction(east = 0, south = east)
        CurrentDirection.WEST -> Direction(east = 0, west = east)
        CurrentDirection.NORTH -> Direction(east = 0, north = east)
    }

    val directionWestRotated = when ((CurrentDirection.WEST + degrees)) {
        CurrentDirection.EAST -> Direction(west = 0, east = west)
        CurrentDirection.SOUTH -> Direction(west = 0, south = west)
        CurrentDirection.WEST -> throw IllegalStateException()
        CurrentDirection.NORTH -> Direction(west = 0, north = west)
    }

    return directionNorthRotated + directionSouthRotated + directionEastRotated + directionWestRotated
}

private operator fun CurrentDirection.plus(that: Int) =
    CurrentDirection.values()[(value + that).rem(CurrentDirection.values().size)]

private enum class CurrentDirection(val value: Int) {
    EAST(0), SOUTH(1), WEST(2), NORTH(3)
}

private data class ShipInstruction(val action: Char, val units: Int)

private data class Direction(
    val east: Int = 0,
    val south: Int = 0,
    val west: Int = 0,
    val north: Int = 0
)

private operator fun Direction.times(that: Int) =
    copy(north = north * that, east = east * that, south = south * that, west = west * that)

private operator fun Direction.plus(that: Direction) =
    copy(
        north = north + that.north,
        east = east + that.east,
        south = south + that.south,
        west = west + that.west
    )