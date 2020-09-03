package algorithm.hackerrank

import java.math.RoundingMode
import kotlin.math.sign


/*
https://www.hackerrank.com/challenges/diagonal-difference/problem
 */

/*
1 2 3
4 5 6
9 8 9
 */

fun diagonalDifferenceOldSchool(arr: Array<Array<Int>>): Int {
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

fun diagonalDifference(arr: Array<Array<Int>>): Int {
    return Math.abs(arr.indices.sumBy { indice -> arr[indice][indice] - arr[indice][arr.size - 1 - indice] })
}

/**
 * https://www.hackerrank.com/challenges/compare-the-triplets/problem
 */
fun compareTriplets(a: Array<Int>, b: Array<Int>): Array<Int> {
    val (alice, bob) = a.zip(b) { a, b -> (a - b).sign }
        .fold(Pair(0, 0), { acc: Pair<Int, Int>, i: Int ->
            when {
                i > 0 -> acc.copy(first = acc.first.inc())
                i < 0 -> acc.copy(second = acc.second.inc())
                else -> acc
            }
        })
    return arrayOf(alice, bob)
}

/**
https://www.hackerrank.com/challenges/a-very-big-sum/problem
 */

fun aVeryBigSum(ar: Array<Long>): Long = ar.sum()


/**
https://www.hackerrank.com/challenges/plus-minus/
 */

fun plusMinus(arr: Array<Int>): Unit {
    println(
        arr.fold(Triple(0, 0, 0), { acc: Triple<Int, Int, Int>, i: Int ->
            when {
                i > 0 -> acc.copy(first = acc.first.inc())
                i < 0 -> acc.copy(second = acc.second.inc())
                else -> acc.copy(third = acc.third.inc())
            }
        }).toList().zip(listOf(RoundingMode.CEILING, RoundingMode.FLOOR, RoundingMode.CEILING))
        { number, roundingMode ->
            number.toBigDecimal().divide(arr.size.toBigDecimal(), 6, roundingMode)
        }.joinToString("\n")
    )
}