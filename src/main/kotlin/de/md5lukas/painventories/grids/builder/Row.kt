package de.md5lukas.painventories.grids.builder

import de.md5lukas.painventories.slots.Slot
import de.md5lukas.painventories.slots.StaticSlot

class Row(
    private val columnAmount: Int
) {

    internal val columns: MutableList<Slot> = MutableList(columnAmount) { StaticSlot.AIR }
    private var columnCounter = 0

    fun <T : Slot> slot(t: T, item: T.() -> Unit) {
        if (columnCounter + 1 == columnAmount) {
            throw IndexOutOfBoundsException(
                "The next column would be $columnCounter, but that is located outside the available columns"
            )
        }
        columns[columnCounter++] = t.apply(item)
    }

    fun <T : Slot> slot(t: T, index: Int, item: T.() -> Unit) {
        columns[index] = t.apply(item)
    }
}