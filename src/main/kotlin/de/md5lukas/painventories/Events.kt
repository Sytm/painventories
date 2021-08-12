package de.md5lukas.painventories

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class defining a PainVentories event involving a player
 * @property player The player that has caused the event
 */
sealed class PVEvent constructor(val player: Player)

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
