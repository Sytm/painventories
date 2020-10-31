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

package de.md5lukas.painventories.grids

import de.md5lukas.painventories.slots.Slot
import de.md5lukas.painventories.slots.StaticSlot

class BasicGrid(
    override val rows: Int,
    override val columns: Int,
    defaultValue: Slot = StaticSlot.AIR
) : Grid {

    private val grid: List<MutableList<Slot>> = List(rows) {
        MutableList(columns) {
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

    override fun forEachSet(action: (row: Int, column: Int) -> Slot) {
        grid.forEachIndexed { rowNumber, row ->
            row.forEachIndexed { columnNumber, _ ->
                grid[rowNumber][columnNumber] = action(rowNumber, columnNumber)
            }
        }
    }

    override operator fun get(row: Int, column: Int): Slot = grid[row][column]

    override operator fun set(row: Int, column: Int, slot: Slot) {
        grid[row][column] = slot
    }

    override fun asList(): List<List<Slot>> = grid
}