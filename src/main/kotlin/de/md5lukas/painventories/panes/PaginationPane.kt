package de.md5lukas.painventories.panes

import com.google.common.base.Preconditions
import de.md5lukas.commons.collections.PaginationList
import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.grids.StaticGrid
import de.md5lukas.painventories.slots.Slot

class PaginationPane(rows: Int, columns: Int) : AbstractDefaultablePane(rows, columns) {

    private val items: PaginationList<() -> Slot> = PaginationList(rows * columns)

    var page: Int = 0
        set(value) {
            Preconditions.checkArgument(
                value == 0 || (value >= 0 && value < items.pages()),
                "The new page is out of bounds"
            )
            field = value
        }
    private var static = StaticGrid(rows, columns)

    override val grid: Grid
        get() {
            if (updated) {
                rebuildGrid()
            }
            return static
        }

    fun previousPage() {
        if (page <= 0) return
        updated = true
        page--
    }

    fun nextPage() {
        if (page + 1 >= items.pages()) return
        page++
    }

    fun setItems(newItems: List<() -> Slot>) {
        items.clear()
        items.addAll(newItems)
    }

    private fun rebuildGrid() {
        val ite = items.page(page).iterator()
        static.forEachSet { _, _ ->
            if (ite.hasNext()) {
                ite.next()?.invoke() ?: defaultSlot
            } else {
                defaultSlot
            }
        }
    }
}