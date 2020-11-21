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

@file:JvmName("PV")

package de.md5lukas.painventories

import de.md5lukas.painventories.panes.Pane
import de.md5lukas.painventories.panes.PatternPane
import de.md5lukas.painventories.panes.ScrollPane
import de.md5lukas.painventories.panes.layout.LayoutPane
import de.md5lukas.painventories.slots.EditableSlot
import de.md5lukas.painventories.slots.NormalSlot
import org.bukkit.entity.Player

/**
 * Creates a [PainVentoryOptions] instance and initializes it
 *
 * @param init Initializer block
 */
inline fun painVentoryOptions(init: PainVentoryOptions.() -> Unit) = PainVentoryOptions().apply(init)

/**
 * Creates a [PainVentory] for the [player] and initializes it
 *
 * @param init Initializer block
 */
inline fun painVentory(opts: PainVentoryOptions, player: Player, init: PainVentory.() -> Unit) =
    PainVentory(opts, player).apply(init)

/**
 * Creates a [ScrollPane] with the given size and initializes it
 *
 * @param init Initializer block
 */
inline fun scrollPane(rows: Int, column: Int, wrappedPane: Pane, init: ScrollPane.() -> Unit) =
    ScrollPane(rows, column, wrappedPane).apply(init)

/**
 * Creates a [PatternPane] with the given size and initializes it
 *
 * @param init Initializer block
 */
inline fun patternPane(rows: Int, columns: Int, init: PatternPane.() -> Unit) =
    PatternPane(rows, columns).apply(init)

/**
 * Creates a [LayoutPane] with the given size and initializes it
 *
 * @param init Initializer block
 */
inline fun layoutPane(rows: Int, columns: Int, init: LayoutPane.() -> Unit) = LayoutPane(rows, columns).apply(init)

/**
 * Creates a [NormalSlot] and initializes it
 *
 * @param init Initializer block
 */
inline fun normalSlot(init: NormalSlot.() -> Unit) = NormalSlot().apply(init)

/**
 * Creates an [EditableSlot] and initializes it
 *
 * @param init Initializer block
 */
inline fun editableSlot(init: EditableSlot.() -> Unit) = EditableSlot().apply(init)
