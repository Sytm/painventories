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