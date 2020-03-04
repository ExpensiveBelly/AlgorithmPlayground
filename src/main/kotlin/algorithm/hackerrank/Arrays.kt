package algorithm.hackerrank

fun main() {
    println(
        hourglassSum(
            arrayOf(
                arrayOf(1, 1, 1, 0, 0, 0),
                arrayOf(0, 1, 0, 0, 0, 0),
                arrayOf(1, 1, 1, 0, 0, 0),
                arrayOf(0, 0, 2, 4, 4, 0),
                arrayOf(0, 0, 0, 2, 0, 0),
                arrayOf(0, 0, 1, 2, 4, 0)
            )
        )
    )
}

/*
https://www.hackerrank.com/challenges/2d-array/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
 */

/*
//1 1 1 0 0 0
//0 1 0 0 0 0
//1 1 1 0 0 0
//0 0 2 4 4 0
//0 0 0 2 0 0
//0 0 1 2 4 0
 */
fun hourglassSum(arr: Array<Array<Int>>): Int {
    val hourGlassMatrix = arr.map {
        (0 until 5).map { x ->
            it.toList().drop(x).take(3).windowed(3)
        }.filter { it.isNotEmpty() }
    }
//    println(hourGlassMatrix)

    println("hourGlass")
    hourGlassMatrix.forEachIndexed { index, list ->
        println("index: $index list: ${list.size} $list")
    }

    val sixteenHourglassMatrixes = (0 until 4).map { x ->
        (0 until 6).map { y ->
//            println("x: $x y: $y hourGlassMatrix: ${hourGlassMatrix[y][x]}")
            hourGlassMatrix[y][x]
        }
    }

    println("sixteenHourglassMatrixes")
    sixteenHourglassMatrixes.forEachIndexed { index, list ->
        println("index: $index list: ${list.size} $list")
    }

    return (flatten(sixteenHourglassMatrixes) as List<Int>).chunked(3).chunked(3)
        .map { it.first().sum() + it[2][1] + it.last().sum() }.max()!!
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