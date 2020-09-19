/*
 *     PainVentories - Library to create inventories in Spigot using panes
 *     Copyright (C) 2020 Lukas Planz <lukas.planz@web.de>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
