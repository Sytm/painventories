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

/**
 * A grid that delegates gets to the function provided at creation.
 *
 * @param getter The function that gets called to get the content of a slot using row and column
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

    override fun asList(): List<List<Slot>> {
        return List(rows) { row ->
            List(columns) { column ->
                getter(row, column)
            }
        }
    }
}