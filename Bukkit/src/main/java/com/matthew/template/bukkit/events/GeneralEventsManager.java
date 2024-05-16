package com.matthew.template.bukkit.events;

import com.matthew.template.bukkit.events.listeners.CowListener;
import com.matthew.template.bukkit.events.listeners.player.PlayerDataListener;
import com.matthew.template.bukkit.events.listeners.player.PlayerListener;
import com.matthew.template.common.apis.GeneralManager;
import com.matthew.template.bukkit.events.listeners.KittenCannonListener;
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
        Bukkit.getPluginManager().registerEvents(new KittenCannonListener(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(PlayerListener.getInstance(), plugin);
        Bukkit.getPluginManager().registerEvents(new CowListener(), plugin);
    }

    @Override
    public void unregister() {
        //no allocated resources needing to be unregistered
    }
}
