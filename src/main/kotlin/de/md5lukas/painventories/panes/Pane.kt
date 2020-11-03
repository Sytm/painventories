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

import de.md5lukas.painventories.Updatable
import de.md5lukas.painventories.grids.Grid

/**
 * A basic definition of a pane
 */
interface Pane : Updatable {

    /**
     * The amount of rows of the pane
     */
    val rows: Int

    /**
     * The amount of columns of the pane
     */
    val columns: Int

    /**
     * A representation of the pane as a grid
     */
    val grid: Grid

    /**
     * If the pane is invisible it will not be shown
     */
    var visible: Boolean
}