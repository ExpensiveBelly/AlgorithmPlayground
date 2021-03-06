package gameoflife

class Universe private constructor(val cells: List<Cell>, val size: Int) {
    constructor(cells: Array<Array<Cell>>) : this(cells.flatten(), cells.size)
    constructor(size: Int) : this(List(size * size) { Cell() }, size)

    fun evolve() =
        Universe(cells.mapIndexed { i, cell -> cell.evolve(cellNeighboursForIndex(i).count { it.isAlive }) }, size)

    private fun cellNeighboursForIndex(index: Int) = neighborCoordinatesOf(index.toX(), index.toY())
        .filter { it.isInBounds() }.map { it.toIndex() }.map { cells[it] }

    private fun neighborCoordinatesOf(x: Int, y: Int) = arrayOf(
        Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1), Pair(x - 1, y),
        Pair(x + 1, y), Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
    )

    fun atPosition(x: Int, y: Int): Cell = cells[(x to y).toIndex()]

    private fun Pair<Int, Int>.isInBounds() = !((first < 0).or(first >= size).or(second < 0).or(second >= size))
    private fun Pair<Int, Int>.toIndex() = second * size + first
    private fun Int.toX() = this % size
    private fun Int.toY() = this / size

    override fun toString(): String {
        return "Universe(cells=$cells, size=$size)"
    }
}