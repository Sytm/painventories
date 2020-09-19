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