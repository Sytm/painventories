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