package algorithm.hackerrank


fun main() {
    println(sockMerchant(3, arrayOf(1, 1, 3, 1, 2, 1, 3, 3, 3, 3)))
    println(countingValleys(8, "UDDDUDUU"))
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
