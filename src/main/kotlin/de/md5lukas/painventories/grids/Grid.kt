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