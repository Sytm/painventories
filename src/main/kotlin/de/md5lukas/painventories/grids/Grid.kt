package de.md5lukas.painventories.grids

import de.md5lukas.painventories.slots.Slot

interface Grid {

    fun forEach(action: ((row: Int, column: Int, slot: Slot) -> Unit))

    fun forEachSet(action: (row: Int, column: Int) -> Slot)

    fun get(row: Int, column: Int): Slot

    fun set(row: Int, column: Int, slot: Slot)

    fun asList(): List<List<Slot>>
}