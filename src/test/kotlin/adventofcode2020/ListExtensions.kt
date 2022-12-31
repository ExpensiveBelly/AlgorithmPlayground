package adventofcode2020


@JvmInline
value class Filename(val value: String)

fun Filename.readChunkedLines(): List<List<String>> = Resources.resourceAsList(value)
    .asSequence()
    .chunked { last, _ -> last.isEmpty() }
    .map { it.filterNot { it.isEmpty() } }
    .toList()

/*
https://discuss.kotlinlang.org/t/splitting-sequence-in-chunks-based-on-predicate/19005/5
 */

fun <T> Sequence<T>.chunked(predicate: (T, T) -> Boolean): Sequence<List<T>> {
    val underlyingSequence = this
    return sequence {
        val buffer = mutableListOf<T>()
        var last: T? = null
        for (current in underlyingSequence) {
            val shouldSplit = last?.let { predicate(it, current) } ?: false
            if (shouldSplit) {
                yield(buffer.toList())
                buffer.clear()
            }
            buffer.add(current)
            last = current
        }
        if (buffer.isNotEmpty()) {
            yield(buffer)
        }
    }
}

/**
 * https://kotlinlang.slack.com/archives/C87V9MQFK/p1672321818262339?thread_ts=1672317042.937679&cid=C87V9MQFK
 */

inline fun <T> Iterable<T>.splitOnce(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val iterator = iterator()
    return Iterable { iterator }.takeWhile { !predicate(it) } to Iterable { iterator }.toList()
}
