package de.md5lukas.painventories.grids

import de.md5lukas.painventories.slots.Slot
import de.md5lukas.painventories.slots.StaticSlot

class StaticGrid(rows: Int, columns: Int) : Grid {

    private val grid: List<MutableList<Slot>> = List(rows) {
        MutableList(columns) {
            StaticSlot.AIR
        }
    }

    override fun forEach(action: (row: Int, column: Int, slot: Slot) -> Unit) {
        grid.forEachIndexed { rowNumber, row ->
            row.forEachIndexed { columnNumber, slot ->
                action(rowNumber, columnNumber, slot)
            }
        }
    }

    override fun forEachSet(action: (row: Int, column: Int) -> Slot) {
        grid.forEachIndexed { rowNumber, row ->
            row.forEachIndexed { columnNumber, _ ->
                grid[rowNumber][columnNumber] = action(rowNumber, columnNumber)
            }
        }
    }

    override fun get(row: Int, column: Int): Slot = grid[row][column]

    override fun set(row: Int, column: Int, slot: Slot) {
        grid[row][column] = slot
    }

    override fun asList(): List<List<Slot>> = grid
}