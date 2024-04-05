package com.matthew.template.generalcommands;

import com.matthew.template.api.GeneralManager;
import com.matthew.template.generalcommands.commands.FireCommand;
import com.matthew.template.generalcommands.commands.SpawnEntityCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class GeneralCommandManager implements GeneralManager {

    private final JavaPlugin plugin;

    public GeneralCommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register() {
        Objects.requireNonNull(plugin.getCommand("spawnentity")).setExecutor(new SpawnEntityCommand());
        Objects.requireNonNull(plugin.getCommand("fire")).setExecutor(new FireCommand(plugin));
    }

    @Override
    public void unregister() {

    }
}
