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