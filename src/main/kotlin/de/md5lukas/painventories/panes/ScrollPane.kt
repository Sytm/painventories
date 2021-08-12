package de.md5lukas.painventories.panes

import de.md5lukas.painventories.grids.BasicGrid
import de.md5lukas.painventories.grids.Grid
import kotlin.math.max
import kotlin.math.min

/**
 * The scroll pane wraps [another pane][wrappedPane]. And the viewable part of the pane can be moved around using
 * the scroll functions
 *
 * @param wrappedPane The pane to wrap initially
 */
class ScrollPane(
    rows: Int,
    columns: Int,
    wrappedPane: Pane,
) : Pane(rows, columns) {

    private val staticGrid = BasicGrid(rows, columns)

    var wrappedPane: Pane = wrappedPane
        set(value) {
            field = value
            updated = true
        }

    override var updated: Boolean
        get() = super.updated || wrappedPane.updated
        set(value) {
            super.updated = value
            wrappedPane.updated = value
        }

    private val maxRowScroll: Int
        get() = max(0, wrappedPane.rows - rows)
    private val maxColumnScroll: Int
        get() = max(0, wrappedPane.columns - columns)

    /**
     * The current amount of rows that have been scrolled.
     *
     * Setting a value will be automatically fixed to be in the min/max range
     */
    var rowScroll: Int = 0
        set(value) {
            val newValue = clamp(0, maxRowScroll, value)
            if (newValue == field)
                return
            field = newValue
            updated = true
        }

    /**
     * The current amount of columns that have been scrolled.
     *
     * Setting a value will be automatically fixed to be in the min/max range
     */
    var columnScroll: Int = 0
        set(value) {
            val newValue = clamp(0, maxColumnScroll, value)
            if (newValue == field)
                return
            field = newValue
            updated = true
        }

    /**
     * Scrolls the viewable portion by the given amount up, if possible
     *
     * @param amount The amount it should get scrolled by (default value is `1`)
     * @return `true` If the value has been changed, `false` otherwise
     */
    fun scrollUp(amount: Int = 1): Boolean {
        if (rowScroll <= 0)
            return false
        rowScroll = max(0, rowScroll - amount)
        updated = true
        return true
    }

    /**
     * Scrolls the viewable portion by the given amount down, if possible
     *
     * @param amount The amount it should get scrolled by (default value is `1`)
     * @return `true` If the value has been changed, `false` otherwise
     */
    fun scrollDown(amount: Int = 1): Boolean {
        val maxScroll = maxRowScroll
        if (rowScroll > maxScroll)
            return false
        rowScroll = min(maxScroll, rowScroll + amount)
        updated = true
        return true
    }

    /**
     * Scrolls the viewable portion by the given amount left, if possible
     *
     * @param amount The amount it should get scrolled by (default value is `1`)
     * @return `true` If the value has been changed, `false` otherwise
     */
    fun scrollLeft(amount: Int = 1): Boolean {
        if (columnScroll <= 0)
            return false
        columnScroll = max(0, columnScroll - amount)
        updated = true
        return true
    }

    /**
     * Scrolls the viewable portion by the given amount right, if possible
     *
     * @param amount The amount it should get scrolled by (default value is `1`)
     * @return `true` If the value has been changed, `false` otherwise
     */
    fun scrollRight(amount: Int = 1): Boolean {
        val maxScroll = maxColumnScroll
        if (columnScroll > maxScroll)
            return false
        columnScroll = min(maxScroll, columnScroll + amount)
        updated = true
        return true
    }

    override val grid: Grid
        get() {
            if (updated) {
                staticGrid.forEachSet { row, column ->
                    val wrappedRow = row + rowScroll
                    val wrappedColumn = column + columnScroll
                    if (wrappedRow < wrappedPane.rows && wrappedColumn < wrappedPane.columns) {
                        wrappedPane.grid.get(wrappedRow, wrappedColumn)
                    } else {
                        defaultSlot
                    }
                }
            }
            return staticGrid
        }

    private fun clamp(min: Int, max: Int, value: Int): Int {
        return max(min, min(max, value))
    }
}