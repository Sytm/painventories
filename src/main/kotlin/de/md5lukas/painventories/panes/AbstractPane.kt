package de.md5lukas.painventories.panes

import com.google.common.base.Preconditions.checkArgument

abstract class AbstractPane(
    final override val rows: Int,
    final override val columns: Int,
) : Pane {

    init {
        checkArgument(rows >= 1, "The row size must be 1 or greater")
        checkArgument(columns >= 1, "The column size must be 1 or greater")
    }

    override var visible: Boolean = true
        set(value) {
            if (field != value) {
                updated = true
            }
            field = value
        }
    override var updated: Boolean = true

}
