package de.md5lukas.painventories.grids

import de.md5lukas.painventories.slots.Slot

interface Grid {

    val rows: Int
    
    val columns: Int

    fun forEach(action: ((row: Int, column: Int, slot: Slot) -> Unit))

    fun forEachSet(action: (row: Int, column: Int) -> Slot)

    operator fun get(row: Int, column: Int): Slot

    operator fun set(row: Int, column: Int, slot: Slot)

    fun asList(): List<List<Slot>>
}