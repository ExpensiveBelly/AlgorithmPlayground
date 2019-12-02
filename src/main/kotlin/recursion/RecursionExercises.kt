package recursion

class RecursionExercises {

    fun toBinary(number: Int): String =
        when (number) {
            0 -> "0"
            1 -> "1"
            else -> "${toBinary(number / 2)}${number % 2}"
        }

    tailrec fun flattenRecTail(lists: List<*>): List<Any> {
        if (lists.all { it !is List<*> }) return lists as List<Any>

        val ret = mutableListOf<Any?>()
        println("lists: $lists")
        lists.forEach { any ->
            if (any is List<*>) ret.addAll(any).also { println("ret for $any: $ret") }
            else ret.add(any)
        }

        return flattenRecTail(ret)
    }
}

fun main() {

    RecursionExercises().run {
        println(flattenRecTail(listOf(1, 2, listOf(3, 4, listOf(5, 6, listOf(7, 8, listOf(9, 10, 11)))), 12, 13)))
    }
}