package kata.bowling

/*
https://github.com/codurance/bowling_game_kata/blob/master/src/main/kotlin/com/codurance/bowlingkata/BowlingScoreCalculator.kt

Bowling Rules
The game consists of 10 frames. In each frame the player has two rolls to knock down 10 pins.
The score for the frame is the total number of pins knocked down, plus bonuses for strikes and spares.
A spare is when the player knocks down all 10 pins in two rolls.
The bonus for that frame is the number of pins knocked down by the next roll.

A strike is when the player knocks down all 10 pins on his first roll.
The frame is then completed with a single roll. The bonus for that frame is the value of the next
two rolls.

In the tenth frame a player who rolls a spare or strike is allowed to roll the extra balls to complete
the frame. However no more than three balls can be rolled in tenth frame.
 */

private const val SPARE = '/'
private const val MISS = '-'
private const val STRIKE = 'X'
private const val NORMAL_FRAMES: Int = 18

fun scoreFor(allRolls: String): Int =
    allRolls.foldIndexed(0) { idx, score, roll ->
        score + score(roll) - spareDiff(allRolls, roll, idx) + bonus(allRolls, roll, idx)
    }

private fun bonus(allRolls: String, roll: Char, idx: Int): Int {
    if (isLastFrame(allRolls, idx)) return 0

    val next = score(allRolls.next(idx))
    return when (roll) {
        SPARE -> next
        STRIKE -> {
            val nextButOne = allRolls.next(idx + 1)
            next + score(nextButOne) - spareDiff(allRolls, nextButOne, idx + 2)
        }
        else -> 0
    }
}

private fun isLastFrame(allRolls: String, idx: Int) =
    NORMAL_FRAMES <= allRolls.substring(0, idx)
        .sumBy { roll -> if (roll == STRIKE) 2 else 1 }

private fun String.next(idx: Int) =
    if (idx < length - 1) this[idx + 1] else MISS

private fun spareDiff(allRolls: String, roll: Char, idx: Int) =
    if (roll == SPARE) score(allRolls.previous(idx)) else 0

private fun String.previous(idx: Int) =
    if (idx > 0) this[idx - 1] else MISS

private fun score(roll: Char) =
    when (roll) {
        in '1'..'9' -> String(charArrayOf(roll)).toInt()
        SPARE, STRIKE -> 10
        else -> 0
    }
