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

import de.md5lukas.painventories.slots.EditableSlot
import de.md5lukas.painventories.slots.NormalSlot
import de.md5lukas.painventories.slots.Slot
import de.md5lukas.painventories.slots.StaticSlot
import org.bukkit.inventory.ItemStack

class Row(
    private val columnAmount: Int
) {

    internal val columns: MutableList<Slot> = MutableList(columnAmount) { StaticSlot.AIR }
    private var columnCounter = 0

    fun <T : Slot> slot(t: T, init: (T.() -> Unit)? = null): T {
        checkCounter()
        init?.let { t.apply(it) }
        columns[columnCounter++] = t
        return t
    }

    fun <T : Slot> slot(index: Int, t: T, init: (T.() -> Unit)? = null): T {
        checkIndex(index)
        init?.let { t.apply(it) }
        columns[index] = t
        return t
    }

    fun editableSlot(init: EditableSlot.() -> Unit): EditableSlot {
        checkCounter()
        return EditableSlot().apply(init).also {
            columns[columnCounter++] = it
        }
    }

    fun editableSlot(index: Int, init: EditableSlot.() -> Unit): EditableSlot {
        checkIndex(index)
        return EditableSlot().apply(init).also {
            columns[index] = it
        }
    }

    fun normalSlot(init: NormalSlot.() -> Unit): NormalSlot {
        checkCounter()
        return NormalSlot().apply(init).also {
            columns[columnCounter++] = it
        }
    }

    fun normalSlot(index: Int, init: NormalSlot.() -> Unit): NormalSlot {
        checkIndex(index)
        return NormalSlot().apply(init).also {
            columns[index] = it
        }
    }

    fun staticSlot(itemStack: ItemStack): StaticSlot {
        checkCounter()
        return StaticSlot(itemStack).also {
            columns[columnCounter++] = it
        }
    }

    fun staticSlot(index: Int, itemStack: ItemStack): StaticSlot {
        checkIndex(index)
        return StaticSlot(itemStack).also {
            columns[index] = it
        }
    }

    private fun checkIndex(index: Int) {
        if (index in 0 until columns.size) {
            throw IndexOutOfBoundsException(
                "The column at index $index exceeds the column size of ${columns.size}"
            )
        }
    }

    private fun checkCounter() {
        if (columnCounter + 1 == columnAmount) {
            throw IndexOutOfBoundsException(
                "The next column would be $columnCounter, but that is located outside the available columns"
            )
        }
    }
}