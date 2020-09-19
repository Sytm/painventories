package de.md5lukas.painventories.event

import de.md5lukas.painventories.PainVentory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SlotClickEvent(
    player: Player,
    val painVentory: PainVentory,
    val clickType: ClickType
) : PVEvent(player)
