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
import de.md5lukas.painventories.panes.Pane
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class PainVentory(options: PainVentoryOptions) {

    val player: Player = options.player

    val rootPane: Pane = options.rootPane
    val title = options.title
    val closeable = options.closeable
    val rows = options.rows
    val size = rows * Constants.INVENTORY_WIDTH
    val onClose = options.onClose

    var inventoryHandle: Inventory? = null

    fun open() {
        PainVentoriesAPI.manager.open(player, this)
    }

    fun close() {
        PainVentoriesAPI.manager.close(player)
    }

    internal fun openInventory() {
        inventoryHandle = Bukkit.createInventory(null, size, title)
        rerenderInventory()
    }

    internal fun rerenderInventory() {
        inventoryHandle?.let { inv ->
            if (!rootPane.updated) {
                return
            }
            rootPane.grid.forEach { row, column, slot ->
                val slotIndex = (row * Constants.INVENTORY_WIDTH) + column
                inv.setItem(slotIndex, slot.getRenderItem(player))
            }
            rootPane.updated = false
        }
    }
}