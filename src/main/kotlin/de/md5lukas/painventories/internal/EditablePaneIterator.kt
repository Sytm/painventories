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

package de.md5lukas.painventories.internal

import de.md5lukas.painventories.panes.EditablePane
import de.md5lukas.painventories.panes.ItemStackWrapper

internal class EditablePaneIterator(private val pane: EditablePane) : AbstractIterator<ItemStackWrapper>() {

    private var currentRow = 0
    private var currentColumn = 0

    override fun computeNext() {
        if (currentRow == pane.rows) {
            done()
            return
        }

        setNext(ItemStackWrapper(pane, currentRow, currentColumn))

        if (++currentColumn == pane.columns) {
            currentColumn = 0
            ++currentRow
        }
    }

}