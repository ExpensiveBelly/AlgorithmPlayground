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
        lists.forEach { any ->
            if (any is List<*>) ret.addAll(any).also { println("ret for $any: $ret") }
            else ret.add(any)
        }

        return flattenRecTail(ret)
    }
}