package algorithm.hackerrank

import java.math.BigInteger
import java.util.stream.Collectors

private data class DistanceAndMessage(val distanceToMouse: Int, val message: String)

fun catAndMouse(x: Int, y: Int, z: Int): String {
    val catA = DistanceAndMessage(abs(x - z), "Cat A")
    val catB = DistanceAndMessage(abs(y - z), "Cat B")

    return when {
        catA.distanceToMouse == catB.distanceToMouse -> "Mouse C"
        catA.distanceToMouse > catB.distanceToMouse -> catB.message
        else -> catA.message
    }
}

fun abs(a: Int): Int {
    return if (a < 0) -a else a
}

fun extraLongFactorials(n: Int): BigInteger {
    return n.factorial()
}

private fun Int.factorial(): BigInteger {
    return factorialInternal(this, BigInteger.ONE)
}

private tailrec fun factorialInternal(n: Int, result: BigInteger): BigInteger = if (n == 0) result
else factorialInternal(n - 1, result.times(BigInteger.valueOf(n.toLong())))

fun findDigits(n: Int): Int {
    return n.toString().toCharArray().count {
        try {
            n.rem(Integer.parseInt(it.toString())) == 0
        } catch (e: ArithmeticException) {
            false
        }
    }
}


fun jumpingOnClouds(c: Array<Int>, k: Int): Int {
    val nextPosition = c.nextPosition(k, 0)
    return jumpingOnCloudsInternal(c, k, nextPosition, 100 - (c.energyToDeductFor(nextPosition)) - 1)
}

private fun Array<Int>.nextPosition(k: Int, currentPos: Int): Int = (currentPos + k) % size

private tailrec fun jumpingOnCloudsInternal(c: Array<Int>, k: Int, pos: Int, energy: Int): Int {
    return if (pos == 0) energy
    else {
        val nextPosition = c.nextPosition(k, pos)
        jumpingOnCloudsInternal(c, k, nextPosition, energy - c.energyToDeductFor(nextPosition) - 1)
    }
}

private fun Array<Int>.energyToDeductFor(nextPosition: Int) = if (this[nextPosition] == 1) 2 else 0


/*
https://www.hackerrank.com/challenges/picking-numbers/problem?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign
 */

fun pickingNumbers(a: Array<Int>): Int {
    return a.toList()
        .distinct()
        .map { number ->
            number to (a.filterNot { abs(it - number) > 1 }).groupingBy { it }.eachCount()
        }
        .maxBy { (first, second) ->
            sum(first, second)
        }?.let { (number, map) -> sum(number, map) } ?: 0
}

private fun sum(number: Int, map: Map<Int, Int>) =
    map.getValue(number) + (map.filterKeys { it != number }.maxBy { it.value }?.value ?: 0)

/*
https://www.hackerrank.com/challenges/climbing-the-leaderboard/problem?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign
 */

fun climbingLeaderboard2(scores: Array<Int>, alice: Array<Int>): Array<Int> {
    return alice.map { number: Int ->
        ((scores + number).distinct().sortedDescending().withIndex().find { it.value == number }?.index ?: 0) + 1
    }.toTypedArray()
}

fun climbingLeaderboard3(scores: Array<Int>, alice: Array<Int>): Array<Int> {
    val scoresDistinct = scores.distinct()
    return alice.toList().stream().parallel()
        .map { number ->
            (scoresDistinct.partition { it > number }.first.size) + 1
        }.collect(Collectors.toList()).toTypedArray()
}

fun climbingLeaderboard4(scores: Array<Int>, alice: Array<Int>): Array<Int> {
    val scoresDistinct = scores.distinct()
    return alice.map { number ->
        var found = false
        var i = 0
        while (!found) {
            if (i == scoresDistinct.size || number >= scoresDistinct[i]) {
                found = true
            } else {
                i += 1
            }
        }
        i + 1
    }.toTypedArray()
}

//climbingLeaderboard(arrayOf(100, 100, 50, 40, 40, 20, 10), arrayOf(5, 25, 50, 120))
//arrayOf(6, 5, 4, 2, 1),

fun climbingLeaderboard(scores: Array<Int>, alice: Array<Int>): Array<Int> {
    val scoresSet = emptySet<Int>().toMutableSet()
    val result: Array<Int> = Array(alice.size) { -1 }
    var scoresIndex = 0
    var aliceIndex = alice.size - 1
    while (aliceIndex >= 0) {
        if (alice[aliceIndex] >= scores[scoresIndex]) {
            result[aliceIndex] = scoresSet.size + 1
            aliceIndex -= 1
        } else {
            scoresSet += scores[scoresIndex]
            if (scoresIndex < scores.size - 1) scoresIndex += 1
            else {
                result[aliceIndex] = scoresSet.size + 1
                aliceIndex -= 1
            }
        }
    }
    return result
}

fun permutationEquation(p: Array<Int>): Array<Int> {
    return p.withIndex().map { indexedValue ->
        compose(function(p), function(p)).invoke(indexedValue.index + 1)
    }.toTypedArray()
}

private fun function(p: Array<Int>): (Int) -> Int = { p.indexOf(it) + 1 }

private fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}

/*
https://www.hackerrank.com/challenges/append-and-delete/problem?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign&h_r=next-challenge&h_v=zen
 */

// "hackerhappy", "hackerrank", 9
// "hackerhappy", "hacker", 9
// "aba", "aba", 7
// "aba", "ab", 7
fun appendAndDelete(s: String, t: String, k: Int): String {
    val minCharsToAppend = t.length - t.commonPrefixWith(s).length
    val minCharsToRemove = s.length - s.commonPrefixWith(t).length
    return if (k > t.length * 2 || (k % 2 == (minCharsToAppend + minCharsToRemove) % 2) && k >= (minCharsToAppend + minCharsToRemove)) "Yes" else "No"
}

/*
https://www.hackerrank.com/challenges/grading/problem?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign
 */

fun gradingStudents(grades: Array<Int>): Array<Int> {
    return grades.map { grade ->
        when {
            grade >= 38 && Math.abs((grade - grade.closestPowerOf5())) < 3 -> grade.closestPowerOf5()
            else -> grade
        }
    }.toTypedArray()
}

fun Int.closestPowerOf5() = 5 * Math.ceil(((this.toDouble() / 5))).toInt()