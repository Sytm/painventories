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

package de.md5lukas.painventories.panes

import de.md5lukas.painventories.grids.BasicGrid
import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.internal.EditablePaneIterator
import de.md5lukas.painventories.slots.EditableSlot
import org.bukkit.inventory.ItemStack

class EditablePane(rows: Int, columns: Int) : AbstractPane(rows, columns), Iterable<ItemStack?> {

    private val basicGrid = BasicGrid(rows, columns)

    init {
        basicGrid.forEachSet { _, _ ->
            EditableSlot()
        }
    }

    override val grid: Grid
        get() = basicGrid

    operator fun set(row: Int, column: Int, stack: ItemStack?) {
        val slot = basicGrid[row, column] as? EditableSlot
            ?: throw IllegalStateException("The slot at R:$row C:$column is not an EditableSlot")
        slot.content = stack
    }

    operator fun get(row: Int, column: Int): ItemStack? {
        val slot = basicGrid[row, column] as? EditableSlot
            ?: throw IllegalStateException("The slot at R:$row C:$column is not an EditableSlot")
        return slot.content
    }

    override operator fun iterator(): Iterator<ItemStack?> = EditablePaneIterator(this)
}
