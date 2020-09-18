package de.md5lukas.painventories.slots

import de.md5lukas.painventories.Updatable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface Slot : Updatable {

    fun getRenderItem(player: Player): ItemStack?
}