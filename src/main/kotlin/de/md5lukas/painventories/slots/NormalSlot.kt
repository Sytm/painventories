package de.md5lukas.painventories.slots

import de.md5lukas.painventories.PainVentoriesAPI
import de.md5lukas.painventories.event.SlotClickEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus.Internal
import java.util.logging.Level

class NormalSlot : AbstractSlot() {

    /**
     * Filter that determines if a player sees the normal item or the fallback item, defaults to the normal item
     */
    var canSee: ((Player) -> Boolean)? = null

    /**
     * Set to `true` if you want the get the click listener run on shift clicks
     */
    var runOnShiftClicks: Boolean = true

    /**
     * Listener that gets called when a player clicks on this slot
     *
     * For this listener to get called [canSee] must allow the player to see the normal item
     */
    var clickListener: ((slot: NormalSlot, event: SlotClickEvent) -> Unit)? = null

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
    fun runClick(event: SlotClickEvent) {
        if (canSee(event.player)) {
            try {
                clickListener?.invoke(this, event)
            } catch (e: Exception) {
                PainVentoriesAPI.plugin.logger.log(
                    Level.SEVERE,
                    "An error occurred while running a clickListener",
                    e
                )
            }
        }
    }

    private fun canSee(player: Player): Boolean {
        return canSee?.invoke(player) ?: true
    }
}