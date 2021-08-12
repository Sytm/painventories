package de.md5lukas.painventories.slots

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A slot that will not change its content after creation with no click events associated
 *
 * @param itemStack The item stack that will be shown
 */
class StaticSlot(private val itemStack: ItemStack?) : Slot() {

    companion object Singletons {
        val EMPTY = StaticSlot(null)
    }

    override fun getRenderItem(player: Player): ItemStack? {
        return itemStack
    }
}