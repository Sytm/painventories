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

package de.md5lukas.painventories.panes.layout

import de.md5lukas.painventories.panes.Pane

/**
 * @property pane The pane itself
 * @property row The row of the top-left most corner of the pane
 * @property column The column of the top-left most corner of the pane
 */
data class LayoutItem<out T : Pane>(
    val pane: T,
    val row: Int,
    val column: Int,
) {
    var visible: Boolean = true
        set(value) {
            if (field != value) {
                visibilityChanged = true
            }
            field = value
        }

    var visibilityChanged: Boolean = false
}