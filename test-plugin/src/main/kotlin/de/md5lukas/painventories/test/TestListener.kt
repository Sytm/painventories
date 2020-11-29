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

package de.md5lukas.painventories.test

import de.md5lukas.painventories.itemStack
import de.md5lukas.painventories.meta
import de.md5lukas.painventories.painVentory
import de.md5lukas.painventories.panes.PaginationPane
import de.md5lukas.painventories.panes.ScrollPane
import de.md5lukas.painventories.patternPane
import de.md5lukas.painventories.slots.StaticSlot
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class TestListener(private val plugin: Plugin) : Listener {

    @EventHandler
    private fun chat(e: AsyncPlayerChatEvent) {
        plugin.server.scheduler.runTask(plugin, Runnable {
            when (e.message[0].toLowerCase()) {
                'p' -> openPageInventory(e.player)
                's' -> openScrollInventory(e.player)
                else -> e.player.sendMessage("Not a valid option")
            }
        })
    }

    private fun openScrollInventory(player: Player) {
        painVentory {
            this.player = player
            title = "§6${player.name}"
            rows = 5

            layout {
                val (sp) = ScrollPane(
                    4,
                    8,
                    patternPane(8, 16) {
                        lines(
                            "XOI",
                            "XOI",
                            "OIX",
                            "OIX",
                        )
                        wrapAround = true

                        defaultValue = StaticSlot(ItemStack(Material.GRASS))

                        'X' staticSlot ItemStack(Material.WHITE_WOOL)
                        'O' staticSlot ItemStack(Material.GRAY_WOOL)
                        'I' staticSlot ItemStack(Material.BLACK_WOOL)
                    }
                ).addToLayout(1, 1)

                patternPane(1, 8) {
                    lines("__l__r__")

                    'l' normalSlot {
                        item = ItemStack(Material.ARROW)
                        clickListener = { _, _ ->
                            sp.scrollLeft()
                        }
                    }
                    'r' normalSlot {
                        item = ItemStack(Material.ARROW)
                        clickListener = { _, _ ->
                            sp.scrollRight()
                        }
                    }
                }.addToLayout(0, 1)

                patternPane(4, 1) {
                    lines(
                        "_",
                        "u",
                        "d",
                        "_"
                    )

                    'u' normalSlot {
                        item = ItemStack(Material.ARROW)
                        clickListener = { _, _ ->
                            sp.scrollUp()
                        }
                    }
                    'd' normalSlot {
                        item = ItemStack(Material.ARROW)
                        clickListener = { _, _ ->
                            sp.scrollDown()
                        }
                    }
                }.addToLayout(1, 0)
            }
            open()
        }
    }

    private fun openPageInventory(player: Player) {
        painVentory {
            this.player = player
            closeable = false
            title = "§6${player.name}"
            rows = 4

            layout {
                val pagination = PaginationPane(3, 9)

                pagination.setItems((0..64).map {
                    return@map {
                        StaticSlot(ItemStack(Material.GRASS_BLOCK, it))
                    }
                })

                pagination.addToLayout(0, 0)

                patternPane(1, 9) {
                    +"_<__x__>_"

                    defaultValue = StaticSlot.EMPTY
                    '<' normalSlot {
                        item = itemStack(Material.ARROW) {
                            meta {
                                setDisplayName("Go back")
                            }
                        }
                        clickListener = { _, _ ->
                            pagination.previousPage()
                        }
                    }
                    '>' normalSlot {
                        item = itemStack(Material.ARROW) {
                            meta {
                                setDisplayName("Go forward")
                            }
                        }
                        clickListener = { _, _ ->
                            pagination.nextPage()
                        }
                    }
                    'x' normalSlot {
                        item = itemStack(Material.ARROW) {
                            meta {
                                setDisplayName("Close")
                            }
                        }
                        clickListener = { _, _ ->
                            this@painVentory.close()
                        }
                    }
                }.addToLayout(3, 0)
            }
            open()
        }
    }
}