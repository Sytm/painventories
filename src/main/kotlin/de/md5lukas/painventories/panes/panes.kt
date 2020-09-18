package de.md5lukas.painventories.panes

import de.md5lukas.painventories.panes.layout.LayoutPane

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