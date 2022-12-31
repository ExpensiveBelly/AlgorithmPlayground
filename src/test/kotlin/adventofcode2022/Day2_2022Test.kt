package adventofcode2022

import Resources
import junit.framework.TestCase.assertEquals
import org.junit.Test

class Day2_2022Test {

    private enum class RockPaperScissors(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        companion object {
            fun of(value: Char): RockPaperScissors = when (value) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw IllegalStateException()
            }
        }
    }

    private fun RockPaperScissors.against(opponent: RockPaperScissors): Int = when (this) {
        RockPaperScissors.ROCK -> when (opponent) {
            RockPaperScissors.ROCK -> 3
            RockPaperScissors.PAPER -> 0
            RockPaperScissors.SCISSORS -> 6
        }

        RockPaperScissors.PAPER -> when (opponent) {
            RockPaperScissors.ROCK -> 6
            RockPaperScissors.PAPER -> 3
            RockPaperScissors.SCISSORS -> 0
        }

        RockPaperScissors.SCISSORS -> when (opponent) {
            RockPaperScissors.ROCK -> 0
            RockPaperScissors.PAPER -> 6
            RockPaperScissors.SCISSORS -> 3
        }
    } + score

    private val input by lazy { Resources.resourceAsList("adventofcode2022/Day2_2022.txt") }

    @Test
    fun `calculate the score you would get if you were to follow the strategy guide`() {
        assertEquals(14297, input
            .sumOf {
                RockPaperScissors.of(it.last()).against(RockPaperScissors.of(it.first()))
            })
    }
}