package com.matthew.template.generalevents;

import com.matthew.template.api.GeneralManager;
import com.matthew.template.generalevents.listeners.CowListener;
import com.matthew.template.generalevents.listeners.KittenCannonListener;
import com.matthew.template.generalevents.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeneralEventsManager implements GeneralManager {

    private final JavaPlugin plugin;

    public GeneralEventsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register() {
        Bukkit.getPluginManager().registerEvents(new KittenCannonListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(PlayerListener.getInstance(), plugin);
        Bukkit.getPluginManager().registerEvents(new CowListener(), plugin);
    }

    @Override
    public void unregister() {
        //no allocated resources needing to be unregistered
    }
}
