package com.matthew.template.bukkit.commands;

import com.matthew.template.bukkit.commands.commands.SpawnEntityCommand;
import com.matthew.template.common.apis.GeneralManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

//Purpose of the manager is simply to keep the entry point of our javaplugin clean and simple
public final class GeneralCommandManager implements GeneralManager {

    private final JavaPlugin plugin;

    public GeneralCommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register() {
        Objects.requireNonNull(plugin.getCommand("spawnentity")).setExecutor(new SpawnEntityCommand());
    }

    @Override
    public void unregister() {
        //no allocated resources in need of teardown
    }
}
