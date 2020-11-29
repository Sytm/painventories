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