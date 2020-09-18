package de.md5lukas.painventories.panes.layout

import de.md5lukas.painventories.panes.Pane

data class LayoutItem(
    val row: Int,
    val column: Int,
    val pane: Pane
)