package recursion

//Problem: What does it print?
class PuzzleProblem {
    fun a(): Nothing = a()

    fun main() {
        a()
    }
}

// Solution:

//tailrec does not work with Nothing type
class PuzzleSolutionNothingTailRec {
    tailrec fun a(): Nothing = a()

    fun main() {
        a()
    }
}


//Solution using generics

class PuzzleSolutionWithGenerics {
    tailrec fun <T> a(): T = a()

    fun main() {
        a<String>()
    }
}
