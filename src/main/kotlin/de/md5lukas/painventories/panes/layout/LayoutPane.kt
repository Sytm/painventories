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

package de.md5lukas.painventories.panes.layout

import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.grids.StaticGrid
import de.md5lukas.painventories.panes.AbstractDefaultablePane
import de.md5lukas.painventories.panes.Pane

class LayoutPane(rows: Int, columns: Int) : AbstractDefaultablePane(rows, columns) {

    private val staticGrid = StaticGrid(rows, columns)
    private val children: MutableList<LayoutItem> = mutableListOf()

    override var updated: Boolean
        get() {
            if (super.updated)
                return true
            children.forEach {
                if (it.pane.updated)
                    return true
            }
            return false
        }
        set(value) {
            super.updated = value
            if (!value) {
                children.forEach {
                    it.pane.updated = value
                }
            }
        }

    override val grid: Grid
        get() {
            if (updated) {
                rebuildGrid()
            }
            return staticGrid
        }

    fun addPane(row: Int, column: Int, pane: Pane) {
        children.add(LayoutItem(row, column, pane))
    }

    private fun rebuildGrid() {
        staticGrid.forEachSet { _, _ -> defaultSlot }
        children.forEach { layoutItem ->
            val p = layoutItem.pane
            if (!p.visible)
                return@forEach
            p.grid.forEach { row, column, slot ->
                val actualRow = row + layoutItem.row
                val actualColumn = column + layoutItem.column
                if (actualRow in 0 until rows && actualColumn in 0 until columns) {
                    staticGrid.set(actualRow, actualColumn, slot)
                }
            }
        }
    }
}