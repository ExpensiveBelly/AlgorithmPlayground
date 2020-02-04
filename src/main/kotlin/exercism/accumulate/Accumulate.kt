package exercism.accumulate

object Accumulate {
    fun <T, R> accumulate(collection: List<T>, converter: (T) -> R): List<R> {
        return collection.map { converter(it) }
    }
}
