package algorithm.hackerrank

import java.util.*

/*
https://www.hackerrank.com/challenges/reduced-string/problem?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign
 */

// baab
// aaabccdddaaa
fun rawSuperReducedString(s: String): String {
    if (s.isEmpty()) return "Empty String"
    var tempList: MutableList<Char> = mutableListOf()
    var result: List<Char> = mutableListOf()
    for (i in 0..s.toCharArray().size) {
        val character = try {
            s[i]
        } catch (e: Exception) {
            null
        }
        when {
            tempList.isEmpty() || tempList.contains(character) -> {
                character?.let { tempList.add(it) }
            }
            else -> {
                if (tempList.count() % 2 != 0) {
                    val takeIf = result.lastOrNull()?.takeIf { it == tempList.first() }
                    if (takeIf != null) {
                        result = result.minus(result.last())
                    } else {
                        result += tempList.first()
                    }
                }
                tempList.clear()
                character?.let { tempList.add(it) }
            }
        }
    }
    val toString = result.joinToString("")
    return if (toString.isEmpty()) return "Empty String" else toString
}

// Stack-based solution
fun superReducedString(s: String): String {
    val stack: Stack<Char> = Stack()
    s.toCharArray().forEach {
        if (stack.isNotEmpty() && stack.peek() == it) stack.pop() else stack.push(it)
    }
    val joinToString = stack.joinToString("")
    return if (joinToString.isEmpty()) "Empty String" else joinToString
}

/*
https://www.hackerrank.com/challenges/camelcase/problem?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign&h_r=next-challenge&h_v=zen
 */

fun camelcase(s: String): Int {
    return s.count { it.isUpperCase() } + 1
}

/*
https://www.hackerrank.com/challenges/separate-the-numbers/problem
 */

//1234
//91011
//99100
//101103
//99910001001
//7891011
//9899100
//999100010001
//919293
fun separateNumbersRaw(s: String): String {
    if (s.isEmpty() || s.length < 2) return "NO"

    var numberOfDigits = 1
    var currentNumber = (s[0] - '0').toLong()
    var expectedNumber = currentNumber + 1
    var i = 1
    val end = s.length
    var isIncreasingNumber = false
    var numbersConsidered = mutableSetOf<Long>()
    while (i < end) {
        if (s.substring(i).startsWith(expectedNumber.toString())) {
            numberOfDigits = expectedNumber.toString().count()
            expectedNumber++
            isIncreasingNumber = true
            i += numberOfDigits
        } else {
            try {
                currentNumber = s.substring(0, currentNumber.toString().count() + 1).toLong()
                if (!numbersConsidered.add(currentNumber)) break
            } catch (e: NumberFormatException) {
                break
            } finally {
                isIncreasingNumber = false
                i = currentNumber.toString().count()
            }
            expectedNumber = currentNumber + 1
        }
    }
    return if (isIncreasingNumber) "YES $currentNumber" else "NO"
}
/*
https://www.hackerrank.com/rest/contests/master/challenges/separate-the-numbers/hackers/alfard/download_solution
 */

fun separateNumbers(s: String): String {
    val matches = (1..s.length / 2).asSequence()
        .map { s.substring(0, it) }
        .map { it.toLong() }
        .map { x -> x to (0..30).map { x + it } }/*.also { println(it.toList()) }*/
        .filter { (_, v) -> (2..30).any { (v.subList(0, it).joinToString("").also { println(it) } == s) } }

    return when (matches.firstOrNull()) {
        null -> "NO"
        else -> "YES ${matches.first().first}"
    }
}

/*
https://www.hackerrank.com/challenges/funny-string/problem?h_r=next-challenge&h_v=legacy
 */

// Complete the funnyString function below.
fun funnyString(s: String): String {
    val asciiValues = s.toCharArray().map { it.toInt() }
    val asciiValuesReverse = s.reversed().toCharArray().map { it.toInt() }
    val adjacent = asciiValues.zipWithNext().map { Math.abs(it.first - it.second) }
    val adjacentReverse = asciiValuesReverse.zipWithNext().map { Math.abs(it.first - it.second) }
    return if (adjacent == adjacentReverse) "Funny" else "Not Funny"
}

/*
https://www.hackerrank.com/challenges/gem-stones/problem?h_r=next-challenge&h_v=legacy&h_r=next-challenge&h_v=zen
 */

fun gemstones(arr: Array<String>) = arr.toList().zipWithNext()
    .map {
        it.first.toCharArray().intersect(it.second.toCharArray().toList()).size
    }.min()


/*
https://www.hackerrank.com/challenges/weighted-uniform-string/problem
 */

private val alphabet: String = "abcdefghijklmnopqrstuvwxyz"

/*
abccddde
1
3
12
5
9
10

This is not good enough in terms of per
 */

fun weightedUniformStrings1(s: String, queries: Array<Int>): Array<String> {
    val map =
        s.toCharArray().toList().pack().map { it.mapIndexed { index, c -> (alphabet.indexOf(c) + 1) * (index + 1) } }
            .flatten()
    return queries.map { if (map.contains(it)) "Yes" else "No" }.toTypedArray()
}

private tailrec fun List<Char>.pack(result: List<List<Char>> = emptyList()): List<List<Char>> =
    if (isEmpty()) result
    else {
        val sublist = takeWhile { it == first() }
        this@pack.drop(sublist.size).pack(result + listOf(sublist))
    }

fun weightedUniformStrings(s: String, queries: Array<Int>): Array<String> {
    val weights = mutableSetOf<Int>()
    var lastItem: Pair<Char, Int>? = null
    s.forEach { c ->
        lastItem = (lastItem?.let { (character, ocurrences) ->
            if (character != c) c.toInitialOcurrence() else c to (ocurrences + 1)
        } ?: (c.toInitialOcurrence())).also { (_, occurrences) -> weights.add(c.toWeight() * occurrences) }
    }
    return queries.map { if (weights.contains(it)) "Yes" else "No" }.toTypedArray()
}

private fun Char.toInitialOcurrence() = this to 1
private fun Char.toWeight() = (this - 'a') + 1

/*
https://www.hackerrank.com/challenges/simple-array-sum/problem
 */

fun simpleArraySum(ar: Array<Int>): Int {
    return ar.sum()
}