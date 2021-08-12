package de.md5lukas.painventories.grids

import de.md5lukas.painventories.slots.Slot

/**
 * A grid that delegates reads to the provided callback function.
 *
 * @param getter The callback function that reads get delegated to using the requested row and slot
 */
class DelegatedGrid(
    override val rows: Int,
    override val columns: Int,
    private val getter: (row: Int, column: Int) -> Slot
) : Grid {

    override fun forEach(action: (row: Int, column: Int, slot: Slot) -> Unit) {
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                action(row, column, getter(row, column))
            }
        }
    }

    override fun get(row: Int, column: Int): Slot {
        return getter(row, column)
    }
}