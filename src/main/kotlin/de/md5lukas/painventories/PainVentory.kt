/*
 *     PainVentories - Library to create inventories in Spigot using panes
 *     Copyright (C) 2020 Lukas Planz <lukas.planz@web.de>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.md5lukas.painventories

import de.md5lukas.painventories.internal.Constants
import de.md5lukas.painventories.panes.layout.LayoutPane
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * A PainVentory is a class that allows creation of GUIs for Bukkit based on the concept of panes that may contain
 */
class PainVentory {

    /**
     * The player that gets the inventory displayed
     */
    lateinit var player: Player

    /**
     * The title for the inventory.
     */
    var title: String = "Default Title"

    /**
     * Set to false to automatically reopen the inventory if the player is trying to close it
     */
    var closeable: Boolean = true

    /**
     * The amount of rows the inventory has. Must be between `1` and `6`
     *
     * @throws IllegalArgumentException If the value is out of range
     */
    var rows: Int = 3
        set(value) {
            if (value !in 1..6) {
                throw IllegalArgumentException("The value is out of bounds (1 - 6)")
            }
            field = value
        }

    private var rowsToUse = rows

    /**
     * Callback is called with the player who has the inventory open and the inventory itself to perform any finishing
     * tasks when the inventory gets closed
     */
    var onClose: ((player: Player, painVentory: PainVentory) -> Unit)? = null

    /**
     * The first time this function gets called, modifications to [rows] will not make any difference
     */
    inline fun layout(init: LayoutPane.() -> Unit) {
        rootPane.apply(init)
    }

    /**
     * The first time this value is accessed, modifications to [rows] will not make any difference
     */
    val rootPane: LayoutPane by lazy(LazyThreadSafetyMode.NONE) {
        rowsToUse = rows
        LayoutPane(rowsToUse, Constants.INVENTORY_WIDTH)
    }

    private var inventoryHandle: Inventory? = null

    /**
     * Opens the PainVentory to the player
     */
    fun open() {
        PainVentoriesAPI.manager.open(player, this)
    }

    /**
     * Closes this PainVentory
     */
    fun close() {
        PainVentoriesAPI.manager.close(player)
    }

    internal fun openInventory() {
        if (inventoryHandle == null) {
            inventoryHandle = Bukkit.createInventory(
                null,
                rowsToUse * Constants.INVENTORY_WIDTH,
                title
            )
        }
        inventoryHandle?.let { player.openInventory(it) }

        // Mark root pane as updated to force rerender
        rootPane.updated = true
        rerenderInventory()
    }

    internal fun rerenderInventory() {
        inventoryHandle?.let { inv ->
            with(rootPane) {
                if (!updated) {
                    return
                }
                grid.forEach { row, column, slot ->
                    val slotIndex = (row * Constants.INVENTORY_WIDTH) + column
                    inv.setItem(slotIndex, slot.getRenderItem(player))
                }
                updated = false
            }
        }
    }
}