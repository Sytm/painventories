package de.md5lukas.painventories.slots

import de.md5lukas.painventories.PainVentoryDsl
import de.md5lukas.painventories.SlotClickEvent
import de.md5lukas.painventories.Updatable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A basic Slot for usage in PainVentories
 */
@PainVentoryDsl
abstract class Slot : Updatable {

    override var updated: Boolean = true

    /**
     * Method that is used by PainVentories to get the [ItemStack] that should be shown to the player
     *
     * @param player The player that should get the item
     * @return The ItemStack to show, if `null` the slot will be empty
     */
    abstract fun getRenderItem(player: Player): ItemStack?

    /**
     * This method is called by PainVentories if the Slot has been clicked, the default implementation is a noop
     */
    open fun runClick(event: SlotClickEvent) {}
}