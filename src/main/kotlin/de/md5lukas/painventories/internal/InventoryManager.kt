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
    plugin: Plugin
) : Listener {

    private val openInventories: MutableMap<Player, PainVentory> = HashMap()
    private val closeQueue: MutableList<Player> = ArrayList()

    init {
        plugin.server.pluginManager.registerEvents(
            this,
            plugin
        )
    }

    fun open(player: Player, painVentory: PainVentory) {
        close(player)
        openInventories[player] = painVentory
        painVentory.openInventory()
    }

    fun close(player: Player) {
        runCloseHook(player)
        closeQueue.add(player)
        player.closeInventory()
    }

    private fun runCloseHook(player: Player) {
        openInventories[player]?.let {
            it.onClose?.invoke(player, it)
        }
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
                val event = SlotContentUpdateEvent(p, e.currentItem ?: ItemStack(Material.AIR))
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
            inv.onClose?.invoke(p, inv)
        } else {
            inv.openInventory()
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private fun onQuit(e: PlayerQuitEvent) {
        runCloseHook(e.player)
    }
}