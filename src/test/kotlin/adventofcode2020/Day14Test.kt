package adventofcode2020

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.pow


class Day14Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day14.txt")
            .fold("" to emptyList<MemoryValue>(), { (currentMask, list), s: String ->
                if (s.startsWith("mask")) s.drop(7) to list
                else currentMask to list + listOf(s.toMemoryValue(currentMask))
            }).second
    }

    @Test
    fun `part 1`() {
        val map = input.fold(emptyMap(), { acc: Map<Int, String>, memoryValue: MemoryValue ->
            val binaryString = memoryValue.value.toBinaryString()
            val binaryValuePadded = binaryString.padStart(memoryValue.bitmask.value.length, '0')
            acc + (memoryValue.address.toInt() to binaryValuePadded.apply(memoryValue.bitmask))
        }).mapValues { it.value.toDecimalNumber() }

        assertEquals(13556564111697, map.values.sum())
    }

    @Test
    fun `part 2`() {
        val map = input.fold(emptyMap(), { acc: Map<Long, Long>, memoryValue: MemoryValue ->
            val binaryAddressPadded =
                memoryValue.address.toBinaryString().padStart(memoryValue.bitmask.value.length, '0')
                    .applyV2(memoryValue.bitmask)
            acc + binaryAddressPadded.toListOfAddresses().associate { it.toDecimalNumber() to memoryValue.value }
        })
        assertEquals(4173715962894, map.values.sum())
    }
}

private fun String.toListOfAddresses(): List<String> {
    val count = count { it == 'X' }
    val number = (0L until 2.0.pow(count).toLong()).map { it.toBinaryString().padStart(count, '0') }
    return number.map { binaryNumber ->
        var positionToReplace = 0
        toCharArray().map { digit ->
            if (digit == 'X') {
                binaryNumber[positionToReplace].also { positionToReplace += 1 }
            } else digit
        }.joinToString("")
    }
}

private fun String.applyV2(bitmask: Bitmask) = bitmask.value.zip(this).map { (mask, value) ->
    when (mask) {
        'X' -> mask
        '0' -> value
        '1' -> '1'
        else -> throw IllegalStateException()
    }
}.joinToString("")

fun String.toDecimalNumber() = binaryToDecimalRecursive(this) // toLong(radix = 2)

private tailrec fun binaryToDecimalRecursive(input: String, index: Int = 0, result: Double = 0.0): Long =
    if (input.isEmpty()) result.toLong()
    else {
        val digit = input.last()
        binaryToDecimalRecursive(
            input = input.dropLast(1),
            index = index + 1,
            result = result + (if (digit == '1') 2.0.pow(index) else 0.0)
        )
    }

private fun String.apply(bitmask: Bitmask) = bitmask.value.zip(this).map { (mask, value) ->
    if (mask == 'X') value else mask
}.joinToString("")

private fun Long.toBinaryString() = decimalToBinaryRecursive(this)

private tailrec fun decimalToBinaryRecursive(long: Long, result: String = ""): String =
    if (long == 0L) {
        if (result.isEmpty()) "0" else result.reversed()
    } else decimalToBinaryRecursive(long.div(2), result + long.rem(2).toString())

private fun String.toMemoryValue(mask: String): MemoryValue {
    val split = split("=", limit = 2)
    val address = split.first().trim().drop(4).dropLast(1).toLong()
    val value = split.last().trim().toLong()
    return MemoryValue(value, address, Bitmask(mask))
}

data class MemoryValue(val value: Long, val address: Long, val bitmask: Bitmask)

@JvmInline
value class Bitmask(val value: String)
