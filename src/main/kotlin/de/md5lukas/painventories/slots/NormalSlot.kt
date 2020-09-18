package de.md5lukas.painventories.slots

import de.md5lukas.painventories.event.SlotInteractEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus.Internal

class NormalSlot : AbstractSlot() {

    /**
     * Filter that determines if a player sees the normal item or the fallback item, defaults to the normal item
     */
    var canSee: ((Player) -> Boolean)? = null

    /**
     * Listener that gets called when a player clicks on this slot
     *
     * For this listener to get called [canSee] must allow the player to see the normal item
     */
    var clickListener: ((slot: NormalSlot, event: SlotInteractEvent) -> Unit)? = null

    /**
     * Item that is shown normally to the player
     */
    var item: ItemStack? = null

    /**
     * Item that is shown to the player if [canSee] determines that he cannot see this item
     */
    var fallbackItem: ItemStack? = null

    override fun getRenderItem(player: Player): ItemStack? {
        return if (canSee(player)) {
            item
        } else {
            fallbackItem
        }
    }

    @Internal
    fun runClick(event: SlotInteractEvent) {
        if (canSee(event.player)) {
            clickListener?.invoke(this, event)
        }
    }

    private fun canSee(player: Player): Boolean {
        return canSee?.invoke(player) ?: true
    }
}