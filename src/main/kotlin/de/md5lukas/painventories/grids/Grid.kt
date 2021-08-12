package de.md5lukas.painventories.grids

import de.md5lukas.painventories.slots.Slot

/**
 * A simple grid with a defined size and functions to iterate over it
 */
interface Grid {

    /**
     * The amount of rows this grid has
     */
    val rows: Int

    /**
     * The amount of columns this grid has
     */
    val columns: Int

    /**
     * Calls the callback with each slot and its row and column
     */
    fun forEach(action: ((row: Int, column: Int, slot: Slot) -> Unit))

    /**
     * Gets the content of the slot at the specified position
     *
     * @param row The row of the slot
     * @param column The column of the slot
     */
    operator fun get(row: Int, column: Int): Slot
}