package arrow.typeclasses

// This code is explained in "Simple dependency management in Kotlin"
// Video: https://skillsmatter.com/skillscasts/12907-simple-dependency-management-in-kotlin

// TODAY'S EXAMPLE
//
// SEE IF A USER IS IN A DATABASE, ELSE REQUEST IT FROM THE NETWORK

typealias Index = Int

data class User(val id: Index)

inline fun <A> realWorld(f: () -> A): A = Math.random().let {
    if (it > 0.0003) {
        return f()
    } else {
        throw RuntimeException("😱 -> $it!")
    }
}
