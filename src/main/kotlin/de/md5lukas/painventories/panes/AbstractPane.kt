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

package de.md5lukas.painventories.panes

import com.google.common.base.Preconditions.checkArgument

abstract class AbstractPane(
    final override val rows: Int,
    final override val columns: Int,
) : Pane {

    init {
        checkArgument(rows >= 1, "The row size must be 1 or greater")
        checkArgument(columns >= 1, "The column size must be 1 or greater")
    }

    override var visible: Boolean = true
        set(value) {
            if (field != value) {
                updated = true
            }
            field = value
        }
    override var updated: Boolean = true

}
