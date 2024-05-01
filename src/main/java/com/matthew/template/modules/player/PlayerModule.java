package com.matthew.template.modules.player;

import com.matthew.template.api.ServerModule;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.listeners.PlayerDataListener;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerModule implements ServerModule {

    private final JavaPlugin plugin;
    private DataStorageModule storageModule;

    public PlayerModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public boolean isLoaded(Player player) {
        return storageModule.isLoaded(player);
    }

    public PlayerData getPlayerData(Player player) {
        return storageModule.getPlayerData(player);
    }

    @Override
    public void setUp() {
        this.storageModule = ServerModuleManager.getInstance().getRegisteredModule(DataStorageModule.class);
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(plugin), plugin);
    }

    @Override
    public void teardown() {

    }
}
