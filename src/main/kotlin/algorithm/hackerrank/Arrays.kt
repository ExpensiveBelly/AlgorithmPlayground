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

//https://www.hackerrank.com/challenges/ctci-array-left-rotation/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays

//1, 2, 3, 4, 5 d: 4
fun rotLeft(a: Array<Int>, d: Int): Array<Int> {
    val originalArraySize = a.size
    return a.shift(d % originalArraySize).also { println(it.toList()) }
}

private fun Array<Int>.shift(n: Int) =
    sliceArray(n until size).also { println(it.toList()) } + sliceArray(0 until n).also {
        println(it.toList())
    }

//https://www.hackerrank.com/challenges/new-year-chaos/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays&h_r=next-challenge&h_v=zen

//2, 1, 5, 3, 4
//1, 2, 5, 3, 7, 8, 6, 4
//This one is not performant enough
fun minimumBribesTimeout(q: Array<Int>): Unit {
    println(q.withIndex().sumBy { indexedValue ->
        val valueForThisIndex = indexedValue.index + 1
        val distance = (indexedValue.value - valueForThisIndex)
        if (distance > 2) {
            println("Too chaotic")
            return
        }
        val indexValue = indexedValue.value.toZeroIndexBased()
        val predecessor = indexValue - 1
        (q.sliceArray(predecessor.coerceAtLeast(0)..q.indexOf(indexedValue.value)).count { it > indexedValue.value })
    })
}

private fun Int.toZeroIndexBased() = minus(1)

//2, 1, 5, 3, 4
//1, 2, 5, 3, 7, 8, 6, 4
fun minimumBribesIterative(q: Array<Int>): Unit {
    var totalBribes = 0;

    var expectedFirst = 1;
    var expectedSecond = 2;
    var expectedThird = 3;

    q.forEach { item ->
        when (item) {
            expectedFirst -> {
                expectedFirst = expectedSecond;
                expectedSecond = expectedThird;
                ++expectedThird;
            }
            expectedSecond -> {
                ++totalBribes;
                expectedSecond = expectedThird;
                ++expectedThird;
            }
            expectedThird -> {
                totalBribes += 2;
                ++expectedThird;
            }
            else -> {
                println("Too chaotic");
                return;
            }
        }
    }

    println(totalBribes);
}

//2, 1, 5, 3, 4
//1, 2, 5, 3, 7, 8, 6, 4
fun minimumBribes(q: Array<Int>): Unit {
    minimumBribesTailRec(q, pos = 0, totalBribes = 0, expected = Triple(1, 2, 3))
}

tailrec fun minimumBribesTailRec(array: Array<Int>, pos: Int, totalBribes: Int, expected: Triple<Int, Int, Int>) {
    when (val value = if (pos < array.size) array[pos] else null) {
        expected.first -> minimumBribesTailRec(
            array,
            pos + 1,
            totalBribes,
            expected.copy(expected.second, expected.third, expected.third + 1)
        )
        expected.second -> minimumBribesTailRec(
            array,
            pos + 1,
            totalBribes + 1,
            expected.copy(second = expected.third, third = expected.third + 1)
        )
        expected.third -> minimumBribesTailRec(
            array,
            pos + 1,
            totalBribes + 2,
            expected.copy(third = expected.third + 1)
        )
        else -> {
            println(if (value == null) totalBribes else "Too chaotic")
        }
    }
}


private fun minimumBribeslsmingSolution(q: Array<Int>) {
    var ans = 0
    for (i in q.size - 1 downTo 0) {
        if (q[i] - (i + 1) > 2) {
            println("Too chaotic")
            return
        }
        val max = Math.max(0, q[i] - 2)
        println("max: $max i : $i")
        repeat(
            (max until i)
                .filter { number -> (q[number] > q[i]) }.size
        ) { ans++ }
    }
    println(ans)
}

