package algorithm.hackerrank


fun main() {
    println(sockMerchant(3, arrayOf(1, 1, 3, 1, 2, 1, 3, 3, 3, 3)))
    println(countingValleys(8, "UDDDUDUU"))
    println(jumpingOnClouds(arrayOf(0, 0, 1, 0, 0, 1, 0)))
    println(jumpingOnClouds(arrayOf(0, 0, 0, 0, 1, 0)))

    println(repeatedString("aba", 10))
    println(repeatedString("a", 1000000000000))
}

/**
 * https://www.hackerrank.com/challenges/sock-merchant/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
 */

fun sockMerchant(n: Int, ar: Array<Int>): Int =
    ar.toList().groupBy { it }
        .map { it.value.count() / 2 }
        .sum()


/*
* https://www.hackerrank.com/challenges/counting-valleys/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
 */

fun countingValleys(n: Int, s: String): Int {
    return s.fold(Counter(0, 0)) { acc: Counter, c: Char ->
        when (c) {
            'D' -> acc.copy(count = acc.count - 1)
            'U' -> {
                val newCount = acc.count + 1
                acc.copy(
                    count = newCount,
                    valleyCount = if (newCount == 0) acc.valleyCount + 1 else acc.valleyCount
                )
            }
            else -> throw IllegalArgumentException()
        }
    }.valleyCount
}

private data class Counter(val count: Int, val valleyCount: Int)

/*
https://www.hackerrank.com/challenges/jumping-on-the-clouds/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
 */

fun jumpingOnClouds(c: Array<Int>): Int {
    return jumpingOnCloudsTailRec(c.toList(), 0)
}

tailrec fun jumpingOnCloudsTailRec(c: List<Int>, stepCount: Int): Int {
    return when {
        c.size == 1 -> stepCount
        c.canJumpTwo() -> jumpingOnCloudsTailRec(c.drop(2), stepCount + 1)
        else -> jumpingOnCloudsTailRec(c.drop(1), stepCount + 1)
    }
}

private fun List<Int>.canJumpTwo(): Boolean {
    return size > 2 && take(3).last().isCumulus()
}

private fun Int.isCumulus(): Boolean {
    return this == 0
}

/*
https://www.hackerrank.com/challenges/repeated-string/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup&h_r=next-challenge&h_v=zen
 */

fun repeatedString(s: String, n: Long): Long {
    val divider = n / s.length
    val mod = n % s.length
    return (countA(s) * divider) + countA(s.take(mod.toInt()))
}

private fun countA(s: String) = s.count { it == 'a' }