package algorithm.geeksforgeeks


fun main() {
    println(minOperations(intArrayOf(40, 6, 40, 20)))
}

/**
 * https://www.geeksforgeeks.org/make-all-the-array-elements-odd-with-minimum-operations-of-given-type/
 */

fun minOperations(arr: IntArray) = minOperationsTailRec(arr.toList().distinct().sortedDescending(), 0)

tailrec fun minOperationsTailRec(list: List<Int>, count: Int): Int = if (list.all { it.isOdd() }) count
else minOperationsTailRec(list.find { !it.isOdd() }.let { oddItem ->
    list.map { item ->
        if (item == oddItem) item.div(2) else item
    }
}, count + 1)

private fun Int.isOdd() = this % 2 != 0
