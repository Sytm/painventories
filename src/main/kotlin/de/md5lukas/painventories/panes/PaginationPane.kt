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

import de.md5lukas.commons.collections.PaginationList
import de.md5lukas.painventories.grids.BasicGrid
import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.slots.Slot

class PaginationPane(rows: Int, columns: Int) : AbstractDefaultablePane(rows, columns) {

    private val items: PaginationList<() -> Slot> = PaginationList(rows * columns)

    var page: Int = 0
        set(value) {
            field = when {
                (value < 0) -> 0
                (value >= items.pages()) -> items.pages() - 1
                else -> value
            }
        }
    private var static = BasicGrid(rows, columns)

    override val grid: Grid
        get() {
            if (updated) {
                rebuildGrid()
            }
            return static
        }

    fun previousPage(): Boolean {
        if (page <= 0) return false
        updated = true
        page--
        return false
    }

    fun nextPage(): Boolean {
        if (page + 1 >= items.pages()) return false
        updated = true
        page++
        return true
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