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

import com.google.common.base.Preconditions
import de.md5lukas.commons.collections.PaginationList
import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.grids.StaticGrid
import de.md5lukas.painventories.slots.Slot

class PaginationPane(rows: Int, columns: Int) : AbstractDefaultablePane(rows, columns) {

    private val items: PaginationList<() -> Slot> = PaginationList(rows * columns)

    var page: Int = 0
        set(value) {
            Preconditions.checkArgument(
                value == 0 || (value >= 0 && value < items.pages()),
                "The new page is out of bounds"
            )
            field = value
        }
    private var static = StaticGrid(rows, columns)

    override val grid: Grid
        get() {
            if (updated) {
                rebuildGrid()
            }
            return static
        }

    fun previousPage() {
        if (page <= 0) return
        updated = true
        page--
    }

    fun nextPage() {
        if (page + 1 >= items.pages()) return
        page++
    }

    fun setItems(newItems: List<() -> Slot>) {
        items.clear()
        items.addAll(newItems)
    }

    private fun rebuildGrid() {
        val ite = items.page(page).iterator()
        static.forEachSet { _, _ ->
            if (ite.hasNext()) {
                ite.next()?.invoke() ?: defaultSlot
            } else {
                defaultSlot
            }
        }
    }
}