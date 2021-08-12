package de.md5lukas.painventories

import de.md5lukas.painventories.internal.InventoryManager
import org.bukkit.plugin.Plugin

object PainVentoriesAPI {

    private var nullablePlugin: Plugin? = null

    /**
     * The plugin that gets used to register the inventory events
     *
     * @throws IllegalStateException set - if another plugin has already been set
     * @throws IllegalStateException get - if no plugin has been set yet
     */
    var plugin: Plugin
        set(value) {
            if (nullablePlugin != null)
                throw IllegalStateException("A plugin has already been assigned for the PainVentoriesAPI (Name: ${plugin.name})")
            nullablePlugin = value
        }
        get() = nullablePlugin ?: throw IllegalStateException("A plugin has not been set yet")

    internal val manager: InventoryManager by lazy(LazyThreadSafetyMode.NONE) {
        InventoryManager(plugin)
    }
}