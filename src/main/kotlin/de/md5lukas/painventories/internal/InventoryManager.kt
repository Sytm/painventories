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

package de.md5lukas.painventories.internal

import de.md5lukas.painventories.PainVentory
import de.md5lukas.painventories.event.SlotClickEvent
import de.md5lukas.painventories.event.SlotContentUpdateEvent
import de.md5lukas.painventories.slots.EditableSlot
import de.md5lukas.painventories.slots.NormalSlot
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

internal class InventoryManager(
    private val plugin: Plugin
) : Listener {

    private val openInventories: MutableMap<Player, PainVentory> = HashMap()
    private val closeQueue: MutableList<Player> = ArrayList()

    init {
        plugin.server.pluginManager.registerEvents(
            this,
            plugin
        )
        plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            for (element in openInventories) {
                element.value.rerenderInventory()
            }
        }, 0, Constants.RERENDER_INTERVAL)
    }

    fun open(player: Player, painVentory: PainVentory) {
        if (player in openInventories) {
            close(player)
        }
        openInventories[player] = painVentory
        painVentory.openInventory()
    }

    fun close(player: Player) {
        closeQueue.add(player)
        player.closeInventory()
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun onClick(e: InventoryClickEvent) {
        val p = e.whoClicked as Player
        val inv = openInventories[p] ?: return

        if (e.action == InventoryAction.NOTHING && e.click != ClickType.MIDDLE) {
            e.isCancelled = true
            return
        }

        if (e.action == InventoryAction.COLLECT_TO_CURSOR || e.action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            e.isCancelled = true
            if (e.click != ClickType.SHIFT_LEFT && e.click != ClickType.SHIFT_RIGHT)
                return
        }

        if (e.clickedInventory == p.openInventory.topInventory) {
            val row: Int = e.slot / 9
            val column: Int = e.slot % 9

            val slot = inv.rootPane.grid[row, column]

            if (slot is EditableSlot) {
                val event = SlotContentUpdateEvent(p, e.cursor ?: ItemStack(Material.AIR))
                slot.onContentUpdate(event)
                e.isCancelled = event.isCancelled
                return
            } else {
                e.isCancelled = true
            }

            if (slot is NormalSlot) {
                if (slot.runOnShiftClicks || !e.isShiftClick)
                    slot.runClick(SlotClickEvent(p, inv, e.click))
                inv.rerenderInventory()
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun onDrag(e: InventoryDragEvent) {
        val p = e.whoClicked as Player
        val inv = openInventories[p] ?: return

        val topSize = p.openInventory.topInventory.size

        val grid = inv.rootPane.grid

        for (slot in e.rawSlots) {
            if (slot >= topSize)
                continue
            val row: Int = slot / 9
            val column: Int = slot % 9
            if (grid[row, column] is EditableSlot)
                continue
            e.isCancelled = true
            return
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun onClose(e: InventoryCloseEvent) {
        val p = e.player as Player
        val inv = openInventories[p]
        if (inv == null) {
            closeQueue.remove(p)
            return
        }
        if (inv.closeable || closeQueue.contains(p)) {
            closeQueue.remove(p)
            openInventories.remove(p)
            inv.onClose?.invoke(p, inv)
        } else {
            nextTick {
                inv.openInventory()
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun onQuit(e: PlayerQuitEvent) {
        openInventories[e.player]?.let { it.onClose?.invoke(e.player, it) }
        openInventories.remove(e.player)
    }

    private fun nextTick(block: () -> Unit) {
        plugin.server.scheduler.runTask(plugin, block)
    }
}