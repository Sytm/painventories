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
import kotlin.math.max
import kotlin.math.min

class ScrollPane(
    rows: Int,
    columns: Int,
    wrappedPane: Pane,
) : AbstractPane(rows, columns) {

    private val staticGrid = BasicGrid(rows, columns)

    var wrappedPane: Pane = wrappedPane
        set(value) {
            field = value
            updated = true
        }

    override var updated: Boolean
        get() = super.updated || wrappedPane.updated
        set(value) {
            super.updated = value
        }

    private val maxRowScroll: Int
        get() = max(0, wrappedPane.rows - rows)
    private val maxColumnScroll: Int
        get() = max(0, wrappedPane.columns - columns)

    var rowScroll: Int = 0
        set(value) {
            val newValue = clamp(0, maxRowScroll, value)
            if (newValue == field)
                return
            field = newValue
            updated = true
        }
    var columnScroll: Int = 0
        set(value) {
            val newValue = clamp(0, maxColumnScroll, value)
            if (newValue == field)
                return
            field = newValue
            updated = true
        }

    fun scrollUp(amount: Int = 1): Boolean {
        if (rowScroll <= 0)
            return false
        rowScroll = max(0, rowScroll - amount)
        updated = true
        return true
    }

    fun scrollDown(amount: Int = 1): Boolean {
        val maxScroll = maxRowScroll
        if (rowScroll > maxScroll)
            return false
        rowScroll = min(maxScroll, rowScroll + amount)
        updated = true
        return true
    }

    fun scrollLeft(amount: Int = 1): Boolean {
        if (columnScroll <= 0)
            return false
        columnScroll = max(0, columnScroll - amount)
        updated = true
        return true
    }

    fun scrollRight(amount: Int = 1): Boolean {
        val maxScroll = maxColumnScroll
        if (columnScroll > maxScroll)
            return false
        columnScroll = min(maxScroll, columnScroll + amount)
        updated = true
        return true
    }

    override val grid: Grid
        get() {
            if (updated) {
                staticGrid.forEachSet { row, column ->
                    val wrappedRow = row + rowScroll
                    val wrappedColumn = column + columnScroll
                    if (wrappedRow < wrappedPane.rows && wrappedColumn < wrappedPane.columns) {
                        wrappedPane.grid.get(wrappedRow, wrappedColumn)
                    } else {
                        defaultSlot
                    }
                }
            }
            return staticGrid
        }

    private fun clamp(min: Int, max: Int, value: Int): Int {
        return max(min, min(max, value))
    }
}