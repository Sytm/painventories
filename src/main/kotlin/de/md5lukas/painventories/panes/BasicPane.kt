package de.md5lukas.painventories.panes

import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.grids.builder.GridBuilder

class BasicPane(rows: Int = 1, columns: Int = 1) : AbstractPane(rows, columns) {

    private val gridBuilder = GridBuilder(rows, columns)
    override val grid: Grid
        get() = gridBuilder

    fun content(slotsInit: GridBuilder.() -> Unit) {
        gridBuilder.apply(slotsInit)
    }
}