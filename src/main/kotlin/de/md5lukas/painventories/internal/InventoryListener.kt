package de.md5lukas.painventories.internal

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerQuitEvent

class InventoryListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onClick(e: InventoryClickEvent) {

    }

    @EventHandler(priority = EventPriority.LOW)
    fun onDrag(e: InventoryDragEvent) {

    }

    @EventHandler(priority = EventPriority.LOW)
    fun onClose(e: InventoryCloseEvent) {

    }

    @EventHandler(priority = EventPriority.LOW)
    fun onQuit(e: PlayerQuitEvent) {

    }
}