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