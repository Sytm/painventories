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

package de.md5lukas.painventories.dsl

import de.md5lukas.painventories.PainVentory
import de.md5lukas.painventories.PainVentoryOptions
import de.md5lukas.painventories.internal.Constants
import de.md5lukas.painventories.panes.BasicPane
import de.md5lukas.painventories.panes.PaginationPane
import de.md5lukas.painventories.panes.Pane
import de.md5lukas.painventories.panes.ScrollPane
import de.md5lukas.painventories.panes.layout.LayoutPane
import de.md5lukas.painventories.slots.EditableSlot
import de.md5lukas.painventories.slots.NormalSlot
import org.bukkit.entity.Player

inline fun normalSlot(init: NormalSlot.() -> Unit): NormalSlot {
    return NormalSlot().apply(init)
}

inline fun editableSlot(init: EditableSlot.() -> Unit): EditableSlot {
    return EditableSlot().apply(init)
}

inline fun basicPane(rows: Int = 1, columns: Int = 1, init: BasicPane.() -> Unit): BasicPane {
    return BasicPane(rows, columns).apply(init)
}

inline fun scrollPane(rows: Int, columns: Int, init: ScrollPane.() -> Unit): ScrollPane {
    return ScrollPane(rows, columns).apply(init)
}

inline fun paginationPane(rows: Int, columns: Int, init: PaginationPane.() -> Unit): PaginationPane {
    return PaginationPane(rows, columns).apply(init)
}

inline fun layoutPane(rows: Int, columns: Int, init: LayoutPane.() -> Unit): LayoutPane {
    return LayoutPane(rows, columns).apply(init)
}

inline fun painVentory(options: PainVentoryOptions.() -> Unit): PainVentory {
    return PainVentory(PainVentoryOptions().apply(options))
}