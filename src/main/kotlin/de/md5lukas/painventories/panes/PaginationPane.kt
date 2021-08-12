package de.md5lukas.painventories.panes

import de.md5lukas.painventories.grids.BasicGrid
import de.md5lukas.painventories.grids.Grid
import de.md5lukas.painventories.internal.PaginationList
import de.md5lukas.painventories.slots.Slot

/**
 * A pagination pane takes its contents and puts it on different pages depending on the size
 */
class PaginationPane(rows: Int, columns: Int) : Pane(rows, columns) {

    private val items: PaginationList<() -> Slot> = PaginationList(rows * columns)

    var page: Int = 0
    private var static = BasicGrid(rows, columns)

    override val grid: Grid
        get() {
            if (updated) {
                rebuildGrid()
            }
            return static
        }

    /**
     * Advances the page to the next one, if possible
     *
     * @return `true` if the page has been changed, `false` otherwise
     */
    fun previousPage(): Boolean {
        if (page <= 0) return false
        updated = true
        page--
        return false
    }

    /**
     * Goes a page back if possible
     *
     * @return `true` if the page has been changed, `false` otherwise
     */
    fun nextPage(): Boolean {
        if (page + 1 >= items.pages()) return false
        updated = true
        page++
        return true
    }

    /**
     * Updates the items in this pagination pane that are showed
     *
     * @param newItems The array of slot suppliers to retrieve the items on demand
     * @param resetPage If set to true (by default) it will set the current page back to 0
     */
    fun setItems(newItems: List<() -> Slot>, resetPage: Boolean = true) {
        items.clear()
        items.addAll(newItems)
        updated = true
        if (resetPage) {
            page = 0
        } else {
            checkPageBounds()
        }
    }

    private fun checkPageBounds(): Boolean {
        val (changed, value) = when {
            (page < 0) -> true to 0
            (page >= items.pages()) -> true to (items.pages() - 1)
            else -> false to page
        }
        page = value

        return changed
    }

    private fun rebuildGrid() {
        val ite = items.page(page).iterator()
        static.forEachSet { _, _ ->
            if (ite.hasNext()) {
                ite.next().invoke()
            } else {
                defaultSlot
            }
        }
    }
}