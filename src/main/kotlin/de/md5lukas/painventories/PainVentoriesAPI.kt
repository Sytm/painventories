package de.md5lukas.painventories

import org.bukkit.plugin.Plugin

object PainVentoriesAPI {
    private lateinit var plugin: Plugin

    fun setPlugin(plugin: Plugin) {
        this.plugin = plugin
    }

    fun getPlugin(): Plugin {
        return this.plugin
    }
}