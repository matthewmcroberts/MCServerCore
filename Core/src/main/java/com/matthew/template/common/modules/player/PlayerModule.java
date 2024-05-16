package com.matthew.template.common.modules.player;

import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.player.structure.PlayerData;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.storage.DataStorageModule;
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
    }

    @Override
    public void teardown() {

    }
}
