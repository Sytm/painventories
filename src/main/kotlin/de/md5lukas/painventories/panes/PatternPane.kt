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
import de.md5lukas.painventories.slots.NormalSlot
import de.md5lukas.painventories.slots.Slot
import de.md5lukas.painventories.slots.StaticSlot
import org.bukkit.inventory.ItemStack

/**
 * This pane takes a defined pattern in a 2D array. Each character at a position maps to a slot
 */
class PatternPane(rows: Int, columns: Int) :
    Pane(rows, columns) {

    override val grid: Grid = DelegatedGrid(rows, columns) { row, column ->
        this[row, column]
    }

    private val pattern: MutableList<String> = mutableListOf()
    private val mappings: MutableMap<Char, Slot> = mutableMapOf()

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
    var defaultValue: Slot? = null

    /**
     * Adds the provided lines to the pattern
     *
     * Each line must not be empty and the same length as the first one
     */
    fun lines(lines: List<String>) {
        updated = true
        for (line in lines) {
            validateLine(line)
            pattern.add(line)
        }
    }

    /**
     * Adds the provided lines to the pattern
     *
     * Each line must not be empty and the same length as the first one
     */
    fun lines(vararg lines: String) {
        updated = true
        for (line in lines) {
            validateLine(line)
            pattern.add(line)
        }
    }

    /**
     * Adds the provided lines to the pattern
     *
     * The line must not be empty and the same length as the first one
     */
    operator fun String.unaryPlus() {
        validateLine(this)
        updated = true
        pattern.add(this)
    }

    private fun validateLine(line: String) {
        if (line.isEmpty()) {
            throw IllegalArgumentException("The provided line to add is empty")
        }
        if (pattern.isNotEmpty() && patternColumns != line.length) {
            throw IllegalArgumentException("The line does not have the same length (${line.length}) as the first line of the pattern ($patternColumns)")
        }
    }

    /**
     * Maps a character to a [Slot]
     *
     * @receiver The character to map to
     * @param slot The slot to map to the character
     */
    infix fun Char.to(slot: Slot) {
        updated = true
        mappings[this] = slot
    }

    /**
     * Creates a [StaticSlot] based on the provided [ItemStack] and maps that to a character
     *
     * @receiver The character to map to
     * @param stack The ItemStack for the StaticSlot
     */
    infix fun Char.staticSlot(stack: ItemStack) {
        updated = true
        this to StaticSlot(stack)
    }

    /**
     * Creates a [NormalSlot] using the provided initializer and maps that to a character
     *
     * @receiver The character to map to
     * @param init The initializer for the NormalSlot
     */
    inline infix fun Char.normalSlot(init: NormalSlot.() -> Unit) {
        updated = true
        this to de.md5lukas.painventories.normalSlot(init)
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

        val slot = mappings.getOrDefault(pattern[varRow][varColumn], defaultValue)

        return slot ?: defaultSlot
    }
}
