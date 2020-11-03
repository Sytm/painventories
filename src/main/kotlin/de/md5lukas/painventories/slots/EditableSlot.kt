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

package de.md5lukas.painventories.slots

import de.md5lukas.painventories.event.SlotContentUpdateEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Slot that a player modify the content of
 */
class EditableSlot(init: EditableSlot.() -> Unit) : AbstractSlot() {

    /**
     * A listener callback that can be optionally provided in the case that the content of the slot has been changed
     */
    var onContentUpdateListener: ((slot: EditableSlot, event: SlotContentUpdateEvent) -> Unit)? = null

    /**
     * The actual item present in the item slot
     */
    var content: ItemStack? = null

    init {
        apply(init)
    }

    override fun getRenderItem(player: Player): ItemStack? {
        return content
    }

    internal fun onContentUpdate(event: SlotContentUpdateEvent) {
        onContentUpdateListener?.invoke(this, event)
        if (!event.isCancelled) {
            content = event.newItem
        }
    }
}