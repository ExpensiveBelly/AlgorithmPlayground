package algorithm.hackerrank

/*
https://www.hackerrank.com/challenges/2d-array/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
 */

fun hourglassSum(arr: Array<Array<Int>>): Int {
    val hourGlassMatrix = arr.map {
        (0 until 5).map { x ->
            it.toList().drop(x).take(3).windowed(3)
        }.filter { it.isNotEmpty() }.flatten()
    }

    println("hourGlass")
    hourGlassMatrix.forEachIndexed { index, list ->
        println("index: $index list: ${list.size} $list")
    }

    val sixteenHourglassMatrixes = (0 until 4).map { x ->
        (0 until 3).map { y ->
            (0 until 6).map { z ->
                tryOrNull { hourGlassMatrix[y + z][x] }
            }.mapNotNull { it }
        }
    }.flatten().map { if (it.size == 6) it else it.take(3) }

    println("sixteenHourglassMatrixes")
    sixteenHourglassMatrixes.forEachIndexed { index, list ->
        println("index: $index list: ${list.size} $list")
    }

    val chunked = (flatten(sixteenHourglassMatrixes) as List<Int>).chunked(3).chunked(3)

    println()
    println("chunked")
    return chunked
        .mapIndexed { index, list ->
            println("index: $index list: ${list.size} $list")
            list.first().sum()
                .also { print("first: ${list.first().sum()} ") } + list[1][1].also { print("mid: $it") } + list.last()
                .sum().also { println(" last: $it") }
        }.also { println(it) }.max()!!
}

fun <T : Any> tryOrNull(body: () -> T?): T? = try {
    body()
} catch (e: Exception) {
    null
}

@Suppress("UNCHECKED_CAST")
private tailrec fun flatten(list: List<Any>): List<Any> {
    if (list.all { it !is List<*> }) return list

    val ret = mutableListOf<Any?>()
    list.forEach { any ->
        if (any is List<*>) ret.addAll(any)
        else ret.add(any)
    }

    return flatten(ret as List<Any>)
}