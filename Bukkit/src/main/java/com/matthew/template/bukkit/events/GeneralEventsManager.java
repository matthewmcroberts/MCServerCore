package com.matthew.template.bukkit.events;

import com.matthew.template.bukkit.events.listeners.PlayerDataListener;
import com.matthew.template.common.apis.GeneralManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeneralEventsManager implements GeneralManager {

    private final JavaPlugin plugin;

    public GeneralEventsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register() {
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(plugin), plugin);
    }

    @Override
    public void unregister() {
        //no allocated resources needing to be unregistered
    }
}
