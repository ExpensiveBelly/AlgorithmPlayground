package gameoflife

import org.junit.Assert.*
import org.junit.Test

class UniverseTest {

    @Test
    fun universeShouldBeGeneratedWithGivenSize() {
        val universe = Universe(size = 3)
        assertEquals(9, universe.cells.size)
    }

    @Test
    fun universeShouldEvolveWithUnderpopulation() {
        val universe = Universe(
            arrayOf(
                arrayOf(Cell(), Cell(), Cell()),
                arrayOf(Cell(), Cell(true), Cell()),
                arrayOf(Cell(), Cell(), Cell(true))
            )
        )
        val evolvedUniverse = universe.evolve()

        assertFalse(evolvedUniverse.cellAt(1, 1).isAlive)
        assertFalse(evolvedUniverse.cellAt(2, 2).isAlive)
    }

    @Test
    fun universeShouldEvolve() {
        val universe = Universe(
            arrayOf(
                arrayOf(Cell(), Cell(), Cell()),
                arrayOf(Cell(), Cell(true), Cell(true)),
                arrayOf(Cell(), Cell(), Cell(true))
            )
        )

        val evolvedUniverse = universe.evolve()

        assertTrue(evolvedUniverse.cellAt(1, 1).isAlive)
        assertTrue(evolvedUniverse.cellAt(1, 2).isAlive)
        assertTrue(evolvedUniverse.cellAt(2, 2).isAlive)
        assertTrue(evolvedUniverse.cellAt(2, 1).isAlive)
    }

    @Test
    fun universeShouldEvolveWithOverpopulation() {
        val universe = Universe(
            arrayOf(
                arrayOf(Cell(true), Cell(), Cell(true)),
                arrayOf(Cell(), Cell(true), Cell()),
                arrayOf(Cell(true), Cell(), Cell(true))
            )
        )

        val evolvedUniverse = universe.evolve()

        assertFalse(evolvedUniverse.cellAt(0, 0).isAlive)
        assertFalse(evolvedUniverse.cellAt(2, 0).isAlive)
        assertFalse(evolvedUniverse.cellAt(1, 1).isAlive)
        assertFalse(evolvedUniverse.cellAt(0, 2).isAlive)
        assertFalse(evolvedUniverse.cellAt(2, 2).isAlive)
    }

    @Test
    fun universeShouldEvolveWithRebirth() {
        val universe = Universe(
            arrayOf(
                arrayOf(Cell(true), Cell(), Cell(true)),
                arrayOf(Cell(), Cell(), Cell()),
                arrayOf(Cell(true), Cell(), Cell())
            )
        )

        val evolvedUniverse = universe.evolve()

        assertFalse(evolvedUniverse.cellAt(0, 0).isAlive)
        assertFalse(evolvedUniverse.cellAt(2, 0).isAlive)
        assertFalse(evolvedUniverse.cellAt(0, 2).isAlive)
        assertTrue(evolvedUniverse.cellAt(1, 1).isAlive)
    }

    private fun Universe.cellAt(x: Int, y: Int): Cell = atPosition(x, y)
}
