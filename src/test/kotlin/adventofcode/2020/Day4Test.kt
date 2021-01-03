package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.sequences.Sequence
import kotlin.sequences.count
import kotlin.sequences.map
import kotlin.sequences.sequence

private const val BirthYear = "byr"
private const val IssueYear = "iyr"
private const val ExpirationYear = "eyr"
private const val Height = "hgt"
private const val HairColor = "hcl"
private const val EyeColor = "ecl"
private const val PassportID = "pid"
private const val CountryID = "cid"

/**
https://adventofcode.com/2020/day/4
 */

class Day4Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day4.txt")
            .asSequence()
            .chunked { last, _ -> last.isEmpty() }
            .map { list: List<String> -> list.toListOfPairs() }
    }

    @Test
    fun `part 1`() {
        val expectedFields = listOf(BirthYear, IssueYear, ExpirationYear, Height, HairColor, EyeColor, PassportID)
        assertEquals(192,
            input.count { list ->
                list.map { it.first }.containsAll(expectedFields)
            })
    }

    @Test
    fun `part 2`() {
        val expectedFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        assertEquals(101,
            input.count { passportFields ->
                passportFields.map { it.first }.containsAll(expectedFields) && passportFields.isValid()
            })
    }
}

private fun List<Pair<String, String>>.isValid() = all { (field, value) ->
    when (field) {
        BirthYear -> value.isInRange(1920..2002)
        IssueYear -> value.isInRange(2010..2020)
        ExpirationYear -> value.isInRange(2020..2030)
        Height -> when (value.takeLast(2)) {
            "cm" -> value.take(3).isInRange(150..193)
            "in" -> value.take(2).isInRange(59..76)
            else -> false
        }
        HairColor -> value.startsWith('#') && value.drop(1).contains("[a-f0-9]{6}".toRegex())
        EyeColor -> value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        PassportID -> value.count() == 9 && value.all { it.isDigit() }
        CountryID -> true
        else -> false
    }
}

private fun String.isInRange(range: IntRange) = toIntOrNull()?.let { it in range } == true

private fun List<String>.toListOfPairs() =
    map { s ->
        s.split(" ")
            .map { it.split(":", limit = 2) }
            .map { it.first() to it.last() }
            .filter { it.first.trim().isNotEmpty() }
    }.flatten()

/*
https://discuss.kotlinlang.org/t/splitting-sequence-in-chunks-based-on-predicate/19005/5
 */

private fun <T> Sequence<T>.chunked(predicate: (T, T) -> Boolean): Sequence<List<T>> {
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
