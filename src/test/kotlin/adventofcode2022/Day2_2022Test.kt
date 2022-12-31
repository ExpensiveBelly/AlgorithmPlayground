package adventofcode2022

import Resources
import junit.framework.TestCase.assertEquals
import org.junit.Test

class Day2_2022Test {

    private enum class RockPaperScissors(val choiceValue: Int) {
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

    private enum class Score(val value: Int) {
        WIN(6), DRAW(3), LOSE(0);

        companion object {
            fun of(value: Char): Score = when (value) {
                'X' -> LOSE
                'Y' -> DRAW
                'Z' -> WIN
                else -> throw IllegalStateException()
            }
        }
    }

    private fun Score.against(rockPaperScissors: RockPaperScissors): Int = when (this) {
        Score.WIN -> when (rockPaperScissors) {
            RockPaperScissors.ROCK -> RockPaperScissors.PAPER
            RockPaperScissors.PAPER -> RockPaperScissors.SCISSORS
            RockPaperScissors.SCISSORS -> RockPaperScissors.ROCK
        }

        Score.DRAW -> rockPaperScissors
        Score.LOSE -> when (rockPaperScissors) {
            RockPaperScissors.ROCK -> RockPaperScissors.SCISSORS
            RockPaperScissors.PAPER -> RockPaperScissors.ROCK
            RockPaperScissors.SCISSORS -> RockPaperScissors.PAPER
        }
    }.choiceValue + value

    private fun RockPaperScissors.against(opponent: RockPaperScissors): Int = when (this) {
        RockPaperScissors.ROCK -> when (opponent) {
            RockPaperScissors.ROCK -> Score.DRAW
            RockPaperScissors.PAPER -> Score.LOSE
            RockPaperScissors.SCISSORS -> Score.WIN
        }

        RockPaperScissors.PAPER -> when (opponent) {
            RockPaperScissors.ROCK -> Score.WIN
            RockPaperScissors.PAPER -> Score.DRAW
            RockPaperScissors.SCISSORS -> Score.LOSE
        }

        RockPaperScissors.SCISSORS -> when (opponent) {
            RockPaperScissors.ROCK -> Score.LOSE
            RockPaperScissors.PAPER -> Score.WIN
            RockPaperScissors.SCISSORS -> Score.DRAW
        }
    }.value + choiceValue

    private val input by lazy { Resources.resourceAsList("adventofcode2022/Day2_2022.txt") }

    @Test
    fun `calculate the score you would get if you were to follow the strategy guide`() {
        assertEquals(
            14297,
            input.sumOf {
                RockPaperScissors.of(it.last()).against(RockPaperScissors.of(it.first()))
            }
        )
    }

    @Test
    fun `what would your total score be if everything goes exactly according to your strategy guide`() {
        assertEquals(
            10498,
            input.sumOf { Score.of(it.last()).against(RockPaperScissors.of(it.first())) }
        )
    }
}