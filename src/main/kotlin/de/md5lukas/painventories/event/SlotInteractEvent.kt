package de.md5lukas.painventories.event

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SlotInteractEvent(player: Player, val clickType: ClickType) : PVEvent(player)
