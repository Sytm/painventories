package de.md5lukas.painventories.slots

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StaticSlot(private val itemStack: ItemStack) : AbstractSlot() {

    companion object Singletons {
        val AIR = StaticSlot(ItemStack(Material.AIR))
    }

    override fun getRenderItem(player: Player): ItemStack? {
        return itemStack
    }
}