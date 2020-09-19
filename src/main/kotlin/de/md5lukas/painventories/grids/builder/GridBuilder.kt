package de.md5lukas.painventories.grids.builder

import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.slots.Slot

class GridBuilder(
    override val rows: Int,
    override val columns: Int
) : Grid {

    private val rowList: List<Row> = List(rows) { Row(columns) }
    private var rowCounter = 0

    fun row(row: Row.() -> Unit) {
        if (rowCounter + 1 == rows) {
            throw IndexOutOfBoundsException(
                "The next row index would be $rowCounter, but that is located outside the available rows"
            )
        }
        rowList[rowCounter++].apply(row)
    }

    override fun forEach(action: ((row: Int, column: Int, slot: Slot) -> Unit)) {
        rowList.forEachIndexed { rowNumber, row ->
            row.columns.forEachIndexed { columnNumber, slot ->
                action(rowNumber, columnNumber, slot)
            }
        }
    }

    override fun forEachSet(action: (row: Int, column: Int) -> Slot) {
        rowList.forEachIndexed { rowNumber, row ->
            row.columns.forEachIndexed { columnNumber, _ ->
                rowList[rowNumber].columns[columnNumber] = action(rowNumber, columnNumber)
            }
        }
    }

    override operator fun get(row: Int, column: Int): Slot {
        return rowList[row].columns[column]
    }

    override operator fun set(row: Int, column: Int, slot: Slot) {
        rowList[row].columns[column] = slot
    }

    override fun asList(): List<List<Slot>> {
        return rowList.map { it.columns }
    }
}
