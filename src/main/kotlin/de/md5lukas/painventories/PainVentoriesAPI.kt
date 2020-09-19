package de.md5lukas.painventories

import de.md5lukas.painventories.internal.InventoryManager
import org.bukkit.plugin.Plugin

object PainVentoriesAPI {
    private var nullablePlugin: Plugin? = null
    var plugin: Plugin
        set(value) {
            if (nullablePlugin != null)
                throw IllegalStateException("A plugin has already been assigned for the PainVentoriesAPI (Name: ${plugin.name})")
            nullablePlugin = value
        }
        get() = nullablePlugin ?: throw IllegalStateException("A plugin has not been set yet")

    internal val manager: InventoryManager by lazy {
        InventoryManager(plugin)
    }
}