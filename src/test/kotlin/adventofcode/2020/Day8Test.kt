package adventofcode.`2020`

import Resources
import org.junit.Assert.assertEquals
import org.junit.Test

private const val INVALID_INDEX = -1
private const val JUMP_OPERATION = "jmp"
private const val NOP_OPERATION = "nop"

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
        assertEquals(1137, accumulateTillLoop())
    }

    private fun accumulateTillLoop() = execute(input).takeIf { it.type == ResultType.LOOP }?.let { it.accumulator } ?: 0

    @Test
    fun `part 2`() {
        assertEquals(1125, accumulateTillLastInstruction())
    }

    private fun accumulateTillLastInstruction(): Int = listOf(
        indexesOf(NOP_OPERATION).replaceAndCount(JUMP_OPERATION),
        indexesOf(JUMP_OPERATION).replaceAndCount(NOP_OPERATION)
    ).firstOrNull { it != INVALID_INDEX } ?: 0

    private fun Set<Int>.replaceAndCount(operation: String): Int {
        forEach { index ->
            val modifiedInput = input.map { if (it.index == index) it.copy(operation = operation) else it }
            val result = execute(modifiedInput)
            if (result.type == ResultType.LAST_INSTRUCTION) return result.accumulator
        }
        return INVALID_INDEX
    }

    private fun indexesOf(operation: String) =
        input.withIndex().filter { it.value.operation == operation }.map { it.index }.toSet()

    private tailrec fun execute(
        input: List<Instruction>,
        instructionIndex: Int = 0,
        visited: Set<Int> = emptySet(),
        acc: Int = 0
    ): Result = when {
        instructionIndex in visited -> Result(acc, ResultType.LOOP)
        instructionIndex >= input.size -> Result(acc, ResultType.LAST_INSTRUCTION)
        else -> {
            val currentInstruction = input[instructionIndex]
            execute(
                input = input,
                instructionIndex = if (currentInstruction.operation == "jmp") instructionIndex + currentInstruction.argument else instructionIndex + 1,
                visited = visited + instructionIndex,
                acc = if (currentInstruction.operation == "acc") acc + currentInstruction.argument else acc
            )
        }
    }
}

private data class Result(val accumulator: Int, val type: ResultType)

private enum class ResultType {
    LOOP, LAST_INSTRUCTION
}

private data class Instruction(val index: Int, val operation: String, val argument: Int)