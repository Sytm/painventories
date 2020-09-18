package de.md5lukas.painventories.slots

import de.md5lukas.painventories.event.SlotContentUpdateEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus.Internal

class EditableSlot : AbstractSlot() {

    var onContentUpdateListener: ((slot: EditableSlot, event: SlotContentUpdateEvent) -> Unit)? = null

    /**
     * The actual item present in the item slot
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var content: ItemStack? = null

    override fun getRenderItem(player: Player): ItemStack? {
        return content
    }

    @Internal
    fun onContentUpdate(event: SlotContentUpdateEvent) {
        content = event.newItem
        onContentUpdateListener?.invoke(this, event)
    }
}