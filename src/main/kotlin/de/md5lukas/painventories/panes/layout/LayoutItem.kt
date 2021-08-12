package de.md5lukas.painventories.panes.layout

import de.md5lukas.painventories.panes.Pane

/**
 * @property pane The pane itself
 * @property row The row of the top-left most corner of the pane
 * @property column The column of the top-left most corner of the pane
 */
data class LayoutItem<out T : Pane>(
    val pane: T,
    val row: Int,
    val column: Int,
) {
    var visible: Boolean = true
        set(value) {
            if (field != value) {
                visibilityChanged = true
            }
            field = value
        }

    var visibilityChanged: Boolean = false
}