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

import de.md5lukas.painventories.grids.DelegatedGrid
import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.slots.Slot
import de.md5lukas.painventories.slots.StaticSlot

/**
 * This pane takes a defined pattern in a 2D array. Each character at a position maps to a slot converter
 *
 * @param T The type that will be converted to an Slot
 */
class PatternPane<T>(rows: Int, columns: Int) :
    AbstractDefaultablePane(rows, columns) {

    override val grid: Grid = DelegatedGrid(rows, columns) { row, column ->
        this[row, column]
    }

    private lateinit var pattern: Array<String>
    private val mappings: MutableMap<Char, T?> = mutableMapOf()

    private val patternRows: Int
        get() = pattern.size

    private val patternColumns: Int
        get() = pattern[0].length

    /**
     * Set to `true` to keep continuing the pattern if it's end has been reached
     */
    var wrapAround = false

    /**
     * The default value that should be shown
     */
    var defaultValue: T? = null

    /**
     * A callback that converts the arbitrary type [T] to a Slot.
     *
     * If [T] is a [Slot] it will be automatically casted to that type by the default callback
     */
    var slotConverter: (T) -> Slot = {
        if (it is Slot) {
            it
        } else {
            throw NotImplementedError("The slot converter needs to be set for a non-slot pattern pane")
        }
    }

    /**
     * Parses the provided lines and then applies them to this pattern.
     *
     * - The amount of lines cannot be zero
     * - Each line must not be empty and the same length as the first one
     */
    fun lines(lines: List<String>) {
        if (lines.isEmpty()) {
            throw IllegalArgumentException("The lines for the pattern cannot be empty")
        }
        var lineLength: Int = -1

        lines.forEachIndexed { index, line ->
            if (line.isEmpty()) {
                throw IllegalArgumentException("The line at $index has a length of 0")
            }

            if (lineLength == -1) {
                lineLength = line.length
            } else {
                if (line.length != lineLength) {
                    throw IllegalArgumentException(
                        "The line at $index has a length of ${line.length} which does not match the length of the first line ($lineLength)"
                    )
                }
            }
        }

        pattern = lines.toTypedArray()
    }

    /**
     * Parses the provided lines and then applies them to this pattern.
     *
     * - The amount of lines cannot be zero
     * - Each line must not be empty and the same length as the first one
     */
    fun lines(vararg lines: String) {
        lines(lines.toList())
    }

    /**
     * Maps a character to a slot convertible
     */
    infix fun Char.to(t: T) {
        mappings[this] = t
    }

    private operator fun get(row: Int, column: Int): Slot {
        var varRow = row
        var varColumn = column

        if (wrapAround) {
            varRow %= patternRows
            if (varRow < 0)
                varRow += patternRows
            varColumn %= patternColumns
            if (varColumn < 0)
                varColumn += patternColumns
        } else {
            if (row < 0 && row >= pattern.size)
                throw IllegalArgumentException("The row $row is out of bounds ($patternRows)")
            if (column < 0 && column >= pattern[0].length)
                throw IllegalArgumentException("The column $column is out of bounds ($patternColumns)")
        }

        val slot = mappings.getOrDefault(pattern[row][column], defaultValue)

        if (slot != null)
            return slotConverter(slot)

        return StaticSlot.AIR
    }
}
