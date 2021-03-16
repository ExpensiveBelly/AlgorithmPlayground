package boggle

import algorithm.MutableMatrix
import algorithm.createMutableMatrix
import algorithm.mutableMatrixOf


private class BoggleDfs(private val dictionary: List<String>) {

    fun boggle(board: MutableMatrix<Char>): Boolean {
        val seen = createMutableMatrix(board.cols, board.rows) { _, _ -> false }
        for (i in 0 until board.rows) {
            for (j in 0 until board.cols) {
                boggleDfs(board, i, j, "", seen)
            }
        }
        return true
    }

    private fun boggleDfs(
        board: MutableMatrix<Char>,
        colIndex: Int,
        rowIndex: Int,
        current: String,
        seen: MutableMatrix<Boolean>
    ) {
        val string = current + board[colIndex, rowIndex]
        seen[colIndex, rowIndex] = true
        if (dictionary.contains(current)) println(current)

        fun Pair<Int, Int>.isInBounds() =
            !((first < 0).or(first >= board.rows).or(second < 0).or(second >= board.cols))

        fun neighborCoordinatesOf(x: Int, y: Int) = arrayOf(
            Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1), Pair(x - 1, y),
            Pair(x + 1, y), Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
        ).filter { it.isInBounds() }

        val neighbors = neighborCoordinatesOf(colIndex, rowIndex)
        for ((col, row) in neighbors) {
            if (!seen[col, row]) {
                boggleDfs(board, col, row, string, seen)
            }
        }

        seen[colIndex, rowIndex] = false
    }
}

fun main() {
    val dictionary = listOf("GEEKS", "FOR", "QUIZ", "GUQ", "EE", "SEEK")

    val board = mutableMatrixOf(3, 3, 'G', 'I', 'Z', 'U', 'E', 'K', 'Q', 'S', 'E')
    BoggleDfs(dictionary).boggle(board)
}
