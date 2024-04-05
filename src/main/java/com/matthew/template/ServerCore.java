package com.matthew.template;

import com.matthew.template.api.ServerModule;
import com.matthew.template.commands.FireCommand;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.permissions.PermissionsModule;
import com.matthew.template.commands.SpawnEntityCommand;
import com.matthew.template.events.CowListener;
import com.matthew.template.events.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ServerCore extends JavaPlugin {

    private static ServerCore instance;

    private ServerModuleManager moduleManager;

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        moduleManager = ServerModuleManager.getInstance();
        moduleManager.registerModule(new PermissionsModule(this));
        for(ServerModule module: moduleManager.getRegisteredModules()) {
            module.setUp();
        }

        getLogger().info("NativePractice loaded");
    }

    @Override
    public void onDisable() {
        for(ServerModule module: moduleManager.getRegisteredModules()) {
            module.teardown();
        }
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("spawnentity")).setExecutor(new SpawnEntityCommand());
        Objects.requireNonNull(getCommand("fire")).setExecutor(new FireCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(PlayerListener.getInstance(), this);
        getServer().getPluginManager().registerEvents(new CowListener(), this);
    }

    public static ServerCore getInstance() {
        return instance;
    }
}
