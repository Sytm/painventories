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

import de.md5lukas.painventories.PainVentoriesAPI
import de.md5lukas.painventories.event.SlotClickEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.logging.Level

/**
 * A normal slot that can run click events and show different item stacks based on certain conditions
 */
class NormalSlot(init: NormalSlot.() -> Unit) : AbstractSlot() {

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

    init {
        apply(init)
    }

    override fun getRenderItem(player: Player): ItemStack? {
        return if (canSee(player)) {
            item
        } else {
            fallbackItem
        }
    }

    internal fun runClick(event: SlotClickEvent) {
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