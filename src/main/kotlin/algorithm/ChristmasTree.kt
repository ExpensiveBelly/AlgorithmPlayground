package algorithm

/**
 * Paint a Christmas tree
 */

fun main() {
    (0..9).map { print(' ') }
    println('*')
    (0..9).map {
        (it..9).map { print(' ') }
        (0 until it).map { print("00") }
        println('1')
    }
}