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
fun separateNumbers(s: String): String {
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