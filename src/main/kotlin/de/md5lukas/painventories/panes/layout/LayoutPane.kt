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

import de.md5lukas.painventories.grids.DelegatedGrid
import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.panes.AbstractDefaultablePane
import de.md5lukas.painventories.panes.Pane
import de.md5lukas.painventories.slots.Slot

/**
 * A layout pane is a pane that manages multiple children panes at different positions.
 *
 * Panes added later to the layout pane take are shown over the previous panes
 */
class LayoutPane(rows: Int, columns: Int) : AbstractDefaultablePane(rows, columns) {

    private val defaultGetter = {
        this.defaultSlot
    }
    private val getters: Array<Array<() -> Slot>> = Array(rows) {
        Array(columns) {
            defaultGetter
        }
    }
    private val delegatedGrid = DelegatedGrid(rows, columns) { row, column ->
        getters[row][column]()
    }
    private val children: MutableList<LayoutItem> = mutableListOf()

    override var updated: Boolean
        get() {
            if (super.updated)
                return true
            children.forEach {
                if (it.visibilityChanged || it.pane.updated)
                    return true
            }
            return false
        }
        set(value) {
            super.updated = value
            if (!value) {
                children.forEach {
                    it.visibilityChanged = value
                    it.pane.updated = value
                }
            }
        }

    private val visibilitiesChanged: Boolean
        get() {
            children.forEach {
                if (it.visibilityChanged)
                    return true
            }
            return false
        }

    override val grid: Grid
        get() {
            // Only rebuild grid if the layout pane has changed, not it's children or the visibilities of one of the panes
            if (super.updated || this.visibilitiesChanged) {
                rebuildGrid()
            }
            return delegatedGrid
        }

    /**
     * Adds the specified Pane to this pane as a layout option
     */
    fun Pane.addToLayout(row: Int, column: Int): LayoutItem {
        val item = LayoutItem(row, column, this)
        this@LayoutPane.children.add(item)
        this@LayoutPane.updated = true
        return item
    }

    private fun rebuildGrid() {
        getters.forEach {
            it.forEachIndexed { index, _ ->
                it[index] = defaultGetter
            }
        }
        children.forEach { li ->
            val p = li.pane
            if (!li.visible)
                return@forEach
            p.grid.forEach { row, column, _ ->
                val actualRow = row + li.row
                val actualColumn = column + li.column
                if (actualRow in 0 until rows && actualColumn in 0 until columns) {
                    getters[actualRow][actualColumn] = {
                        p.grid[row, column]
                    }
                }
            }
        }
    }
}