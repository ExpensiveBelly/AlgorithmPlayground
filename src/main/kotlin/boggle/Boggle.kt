package boggle

import java.util.*

object Boggle {
    /**
     * Problem:
     * http://www.geeksforgeeks.org/boggle-find-possible-words-board-characters/
     * Given a MxN 2d array of chars, determine all the words that can be formed by combining the chars
     * Can check any adjacent cell to i, so long as the letter has not already been used to form that word.
     * In other words no cell can be in the word twice.
     *
     * @param dictionary a set of words is given to check against.
     * @param board      Approach:
     * For every cell in the grid, DFS on adjacent cells.. checking if the current word of that DFS is in the dictionary.
     * Keep track of the cells that have been used, removing them when done with the DFS to adjacent cells,
     * since they can be used as part of a different word path later.
     */
    fun boggle(dictionary: Set<String>, board: Array<CharArray>): Boolean {
        val seen = Array(board.size) { BooleanArray(board[0].size) }
        for (i in board.indices) {
            for (j in board[0].indices) {
                boggleDfs(dictionary, board, i, j, "", seen)
            }
        }
        return true
    }

    private fun boggleDfs(
        dictionary: Set<String>,
        board: Array<CharArray>,
        colIndex: Int,
        rowIndex: Int,
        current: String,
        seen: Array<BooleanArray>
    ) {
        var current = current
        current += board[colIndex][rowIndex]
        seen[colIndex][rowIndex] = true
//        if (dictionary.contains(current)) {
        println(current)
//        }
        val neighbors = getNeighbors(board, colIndex, rowIndex)
        for (neighbor in neighbors) {
            //check if we have used this index
            if (!seen[neighbor.col][neighbor.row]) {
                boggleDfs(dictionary, board, neighbor.col, neighbor.row, current, seen)
            }
        }
        seen[colIndex][rowIndex] = false
    }

    //private helper that returns list of neighbors..
    //visit all adjacent nodes, checking for array out of bounds.
    private fun getNeighbors(board: Array<CharArray>, colIndex: Int, rowIndex: Int): List<BoardIndex> {
        val neighbors: MutableList<BoardIndex> = LinkedList()
        val colStart = if (colIndex - 1 > -1) colIndex - 1 else colIndex
        val rowStart = if (rowIndex - 1 > -1) rowIndex - 1 else rowIndex
        val colEnd = if (colIndex + 1 < board.size) colIndex + 1 else colIndex
        val rowEnd = if (rowIndex + 1 < board[0].size) rowIndex + 1 else rowIndex
        for (i in colStart..colEnd) {
            for (j in rowStart..rowEnd) {
                neighbors.add(BoardIndex(i, j))
            }
        }
        return neighbors
    }

    //private convenience class for referencing 2D indexes in our board.
    private class BoardIndex(var col: Int, var row: Int)
}