package de.md5lukas.painventories.panes

import de.md5lukas.painventories.slots.StaticSlot

abstract class AbstractDefaultablePane(rows: Int, columns: Int) :
    AbstractPane(rows, columns) {

    var defaultSlot: StaticSlot = StaticSlot.AIR
        set(value) {
            field = value
            updated = true
        }
}