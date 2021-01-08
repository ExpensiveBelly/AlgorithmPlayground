package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test


class Day8Test {

    private val input by lazy {
        Resources.resourceAsList("adventofcode/2020/Day8.txt")
            .asSequence()
            .map { it.split(" ", limit = 2) }
            .withIndex()
            .map { (index, value) -> Instruction(index, value.first(), value.last().toInt()) }
            .toList()
    }

    @Test
    fun `part 1`() {
        assertEquals(1137, (if (input.isEmpty()) 0 else accumulateUntilLoop(input.first())))
    }

    private tailrec fun accumulateUntilLoop(
        instruction: Instruction,
        visited: Set<Int> = emptySet(),
        acc: Int = 0
    ): Int =
        if (instruction.index in visited) acc
        else accumulateUntilLoop(
            instruction = if (instruction.operation == "jmp") input[instruction.index + instruction.argument] else input[instruction.index + 1],
            visited = visited + instruction.index,
            acc = if (instruction.operation == "acc") acc + instruction.argument else acc
        )
}

private data class Instruction(val index: Int, val operation: String, val argument: Int)