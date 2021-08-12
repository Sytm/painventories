@file:JvmName("PV")

package de.md5lukas.painventories

import de.md5lukas.painventories.panes.PatternPane
import de.md5lukas.painventories.panes.layout.LayoutPane
import de.md5lukas.painventories.slots.NormalSlot

/**
 * Creates a [PainVentory] and initializes it
 *
 * @param init Initializer block
 */
inline fun painVentory(init: PainVentory.() -> Unit) =
    PainVentory().apply(init)

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
