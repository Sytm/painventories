package de.md5lukas.painventories.grids

import de.md5lukas.painventories.slots.Slot
import de.md5lukas.painventories.slots.StaticSlot

/**
 * A basic grid implementation that uses a 2D-array for its contents that supports read and write operations
 *
 * @param defaultValue The default value to fill the grid with at it's initialization
 */
class BasicGrid(
    override val rows: Int,
    override val columns: Int,
    defaultValue: Slot = StaticSlot.EMPTY
) : Grid {

    private val grid: Array<Array<Slot>> = Array(rows) {
        Array(columns) {
            defaultValue
        }
    }

    override fun forEach(action: (row: Int, column: Int, slot: Slot) -> Unit) {
        grid.forEachIndexed { rowNumber, row ->
            row.forEachIndexed { columnNumber, slot ->
                action(rowNumber, columnNumber, slot)
            }
        }
    }

    /**
     * Calls the callback with each row and column and the returned value is put in the grid at the same position
     */
    fun forEachSet(action: (row: Int, column: Int) -> Slot) {
        grid.forEachIndexed { rowNumber, row ->
            row.forEachIndexed { columnNumber, _ ->
                grid[rowNumber][columnNumber] = action(rowNumber, columnNumber)
            }
        }
    }

    override operator fun get(row: Int, column: Int): Slot = grid[row][column]

    /**
     * Updates the content in the grid at the specified position
     *
     * @param row The row of the slot
     * @param column The column of the slot
     * @param slot The new content for the slot
     */
    operator fun set(row: Int, column: Int, slot: Slot) {
        grid[row][column] = slot
    }
}