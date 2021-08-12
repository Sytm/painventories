package de.md5lukas.painventories.internal

import com.google.common.base.Preconditions.checkArgument
import kotlin.math.ceil
import kotlin.math.min

/**
 * Creates a new PaginationList with the specified amount of items per page
 *
 * @param itemsPerPage The amount of items that should be on one page
 * @throws IllegalArgumentException If `itemsPerPage` is zero or lower
 */
internal class PaginationList<T>(itemsPerPage: Int) : ArrayList<T>() {
    private val itemsPerPage: Int

    /**
     * Calculates the index in the list where the given page starts
     *
     * @param page The page number
     * @return The index where the page starts
     * @throws IllegalArgumentException If the page is out of bounds
     */
    fun pageStart(page: Int): Int {
        checkArgument(page >= 0 && page < pages(), "The provided page number is out of bounds of the available pages")
        return page * itemsPerPage
    }

    /**
     * Calculates the index in the list where the given page ends
     *
     * @param page The page number
     * @return The index where the page ends
     * @throws IllegalArgumentException If the page is out of bounds
     */
    fun pageEnd(page: Int): Int {
        checkArgument(page >= 0 && page < pages(), "The provided page number is out of bounds of the available pages")
        val end = page * itemsPerPage + itemsPerPage
        return min(size, end)
    }

    /**
     * Calculates the amount of pages this list has
     *
     * @return The amount of pages
     */
    fun pages(): Int = ceil(size.toDouble() / itemsPerPage.toDouble()).toInt()

    /**
     * Gets the content of a page based on the number
     *
     * @param page The page number
     * @return The contents of the page
     * @throws IllegalArgumentException If the page is out of bounds
     * @see List.subList
     */
    fun page(page: Int): List<T> {
        return if (size == 0) emptyList<T>() else subList(pageStart(page), pageEnd(page))
    }

    init {
        checkArgument(
            itemsPerPage > 0,
            "The amount of items per page must be greater than zero, but was %d",
            itemsPerPage
        )
        this.itemsPerPage = itemsPerPage
    }
}