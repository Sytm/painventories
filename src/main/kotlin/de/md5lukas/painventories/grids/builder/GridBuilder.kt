package de.md5lukas.painventories.grids.builder

import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.slots.Slot

class GridBuilder(
    private val rowAmount: Int,
    columnAmount: Int
) : Grid {

    private val rows: List<Row> = List(rowAmount) { Row(columnAmount) }
    private var rowCounter = 0

    fun row(row: Row.() -> Unit) {
        if (rowCounter + 1 == rowAmount) {
            throw IndexOutOfBoundsException(
                "The next row index would be $rowCounter, but that is located outside the available rows"
            )
        }
        rows[rowCounter++].apply(row)
    }

    override fun forEach(action: ((row: Int, column: Int, slot: Slot) -> Unit)) {
        rows.forEachIndexed { rowNumber, row ->
            row.columns.forEachIndexed { columnNumber, slot ->
                action(rowNumber, columnNumber, slot)
            }
        }
    }

    override fun forEachSet(action: (row: Int, column: Int) -> Slot) {
        rows.forEachIndexed { rowNumber, row ->
            row.columns.forEachIndexed { columnNumber, _ ->
                rows[rowNumber].columns[columnNumber] = action(rowNumber, columnNumber)
            }
        }
    }

    override fun get(row: Int, column: Int): Slot {
        return rows[row].columns[column]
    }

    override fun set(row: Int, column: Int, slot: Slot) {
        rows[row].columns[column] = slot
    }

    override fun asList(): List<List<Slot>> {
        return rows.map { it.columns }
    }
}
