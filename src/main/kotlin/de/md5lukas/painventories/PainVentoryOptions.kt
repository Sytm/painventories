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

package de.md5lukas.painventories

import de.md5lukas.painventories.internal.Constants
import de.md5lukas.painventories.panes.BasicPane
import de.md5lukas.painventories.panes.PaginationPane
import de.md5lukas.painventories.panes.Pane
import de.md5lukas.painventories.panes.ScrollPane
import de.md5lukas.painventories.panes.layout.LayoutPane
import org.bukkit.entity.Player

class PainVentoryOptions {
    lateinit var player: Player

    var title: String = "Default Title"
    var closeable: Boolean = true
    var rows: Int = 3
        set(value) {
            if (value !in 1..6) {
                throw IllegalArgumentException("The value is out of bounds (1 - 6)")
            }
            field = value
        }
    var onClose: ((player: Player, painVentory: PainVentory) -> Unit)? = null
    lateinit var rootPane: Pane

    inline fun basicPane(init: BasicPane.() -> Unit) {
        this.rootPane = BasicPane(rows, Constants.INVENTORY_WIDTH).apply(init)
    }

    inline fun scrollPane(init: ScrollPane.() -> Unit) {
        this.rootPane = ScrollPane(rows, Constants.INVENTORY_WIDTH).apply(init)
    }

    inline fun paginationPane(init: PaginationPane.() -> Unit) {
        this.rootPane = PaginationPane(rows, Constants.INVENTORY_WIDTH).apply(init)
    }

    inline fun layoutPane(init: LayoutPane.() -> Unit) {
        this.rootPane = LayoutPane(rows, Constants.INVENTORY_WIDTH).apply(init)
    }
}