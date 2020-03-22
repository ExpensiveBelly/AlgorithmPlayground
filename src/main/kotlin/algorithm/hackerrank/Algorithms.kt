package algorithm.hackerrank

import java.math.BigInteger

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