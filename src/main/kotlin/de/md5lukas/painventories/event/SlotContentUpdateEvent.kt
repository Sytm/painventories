package de.md5lukas.painventories.event

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.inventory.ItemStack

class SlotContentUpdateEvent(player: Player, val newItem: ItemStack) : PVEvent(player), Cancellable {

    private var cancel: Boolean = false

    override fun isCancelled(): Boolean {
        return cancel
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancel = cancel
    }

}