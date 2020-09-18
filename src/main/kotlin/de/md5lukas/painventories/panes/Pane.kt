package de.md5lukas.painventories.panes

import de.md5lukas.painventories.Updatable
import de.md5lukas.painventories.grids.Grid

interface Pane : Updatable {

    val rows: Int
    val columns: Int
    val grid: Grid
    var visible: Boolean
}