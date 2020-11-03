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

package de.md5lukas.painventories.event

import de.md5lukas.painventories.PainVentory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Event that is fired when a player clicks on a slot
 *
 * @property painVentory The PainVentory that contains the clicked slot
 * @property clickType The click type that has caused this event
 */
class SlotClickEvent internal constructor(
    player: Player,
    val painVentory: PainVentory,
    val clickType: ClickType
) : PVEvent(player)
