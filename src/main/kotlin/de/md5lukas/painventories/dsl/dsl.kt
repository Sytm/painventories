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