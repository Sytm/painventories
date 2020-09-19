package de.md5lukas.painventories.internal

import de.md5lukas.painventories.PainVentoriesAPI
import org.bukkit.plugin.java.JavaPlugin

class PainVentories : JavaPlugin() {

    override fun onEnable() {
        PainVentoriesAPI.plugin = this
    }
}