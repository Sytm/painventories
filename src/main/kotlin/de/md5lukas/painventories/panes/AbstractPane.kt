package de.md5lukas.painventories.panes

import com.google.common.base.Preconditions.checkArgument

abstract class AbstractPane(
    final override val rows: Int,
    final override val columns: Int,
) : Pane {
    override var visible: Boolean = true
    override var updated: Boolean = true

    init {
        checkArgument(rows >= 1, "The row size must be 1 or greater")
        checkArgument(columns >= 1, "The column size must be 1 or greater")
    }
}
