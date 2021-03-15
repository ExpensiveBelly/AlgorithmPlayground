package boggle

import algorithm.MutableMatrix
import algorithm.createMutableMatrix
import algorithm.mutableMatrixOf


private class BoggleDfs(private val dictionary: List<String>) {

    private fun String.isWord() = dictionary.any { it.equals(this, ignoreCase = true) }

    private fun findWordsUtil(
        boggle: MutableMatrix<Char>,
        visited: MutableMatrix<Boolean>,
        i: Int,
        j: Int,
        str: String
    ) {
        visited[i, j] = true
        val string = str + boggle[i, j]

//        if (string.isWord())
        println(string)

        (i - 1 until i + 1).filter { it >= 0 && it < boggle.rows }.forEach { row ->
            (j - 1 until j + 1).filter { it >= 0 && it < boggle.cols }.forEach { column ->
                if (!visited[row, column]) {
                    findWordsUtil(boggle, visited, row, column, string)
                }
            }
        }

        visited[i, j] = false
    }

    fun findWords(boggle: MutableMatrix<Char>) {
        val visited = createMutableMatrix<Boolean>(boggle.rows, boggle.cols) { _, _ -> false }
        for (row in 0 until boggle.rows) {
            for (column in 0 until boggle.cols) {
                findWordsUtil(boggle, visited, row, column, "")
            }
        }
    }
}

fun main() {
    val dictionary = listOf("GEEKS", "FOR", "QUIZ", "GUQ", "EE", "SEEK")

    val board = mutableMatrixOf(3, 3, 'G', 'I', 'Z', 'U', 'E', 'K', 'Q', 'S', 'E')
    BoggleDfs(dictionary).findWords(board)

//    val arrayOfArrays = arrayOf(charArrayOf('G', 'I', 'Z'), charArrayOf('U', 'E', 'K'), charArrayOf('Q', 'S', 'E'))
//    Boggle.boggle(dictionary.toSet(), arrayOfArrays)
}
