package com.matthew.template.modules.player;

import com.matthew.template.api.ServerModule;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.entity.Player;

public class PlayerModule implements ServerModule {


    private final DataStorageModule module;

    public PlayerModule() {
        final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
        module = moduleManager.getRegisteredModule(DataStorageModule.class);
    }

    /**
     * Check if player has already been loaded into cache
     *
     * @param player bukkit player
     * @return a boolean for if the specified player is already in the cache
     */
    public boolean isLoaded(Player player) {
        for(PlayerData playerData: module.getAllPlayerData()) {
            if(playerData.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public PlayerData getPlayerData(Player player) {
        for(PlayerData playerData: module.getAllPlayerData()) {
            if(playerData.getName().equals(player.getName())) {
                return playerData;
            }
        }
        return null;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void teardown() {

    }
}
