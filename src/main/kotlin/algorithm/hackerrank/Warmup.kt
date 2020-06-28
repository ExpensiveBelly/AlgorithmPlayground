package algorithm.hackerrank


/*
https://www.hackerrank.com/challenges/diagonal-difference/problem
 */

/*
1 2 3
4 5 6
9 8 9
 */

fun diagonalDifference(arr: Array<Array<Int>>): Int {
    var i = 0
    var j = 0
    var firstDiagonal = 0
    var secondDiagonal = 0
    val size = arr.size
    arr.indices.forEach {
        firstDiagonal += arr[i][j]
        secondDiagonal += arr[i][size - 1 - j]
        i += 1
        j += 1
    }
    return Math.abs(firstDiagonal - secondDiagonal)
}
