package algorithm.hackerrank

fun main() {
//    println(jumpingOnClouds(arrayOf(1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1), 19))
    println(findDigits(12))
}

fun findDigits(n: Int): Int {
    return n.toString().toCharArray().count { number ->
        val i = n.rem(number.toInt())
        println("n: $n it: $number rem: $i")
        println(i)
        i == 0
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