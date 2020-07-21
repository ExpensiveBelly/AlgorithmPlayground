package gameoflife


data class Cell(val isAlive: Boolean = false)

fun Cell.evolve(livingNeighborsCount: Int) = Cell(
    when (livingNeighborsCount) {
        0, 1 -> false
        2 -> isAlive
        3 -> true
        else -> false
    }
)