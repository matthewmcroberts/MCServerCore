package com.matthew.template.modules.player.listeners;

import com.matthew.template.ServerCore;
import com.matthew.template.api.DataStorage;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.PlayerModule;
import com.matthew.template.modules.player.permissions.PermissibleInjector;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.ranks.RankModule;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDataListener implements Listener {

    private final ServerCore instance = ServerCore.getInstance();

    private final JavaPlugin plugin;

    private final DataStorage dataStorage;

    private final DataStorageModule storageModule;
    private final PlayerModule playerModule;
    private final RankModule rankModule;


    public PlayerDataListener(JavaPlugin plugin) {
        final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
        this.plugin = plugin;
        this.dataStorage = instance.getDataStorage();
        this.storageModule = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.playerModule = moduleManager.getRegisteredModule(PlayerModule.class);
        this.rankModule = moduleManager.getRegisteredModule(RankModule.class);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        this.dataStorage.load(player).whenComplete((loadedPlayer, ex) -> {
            if (ex != null) {
                Bukkit.getLogger().severe("Failed to load player data: " + ex.getMessage());
                return;
            }
            if (loadedPlayer == null) {
                try {
                    Rank defaultRank = rankModule.getDefaultRank();
                    PlayerData newPlayer = new PlayerData(player, defaultRank, 0L);
                    newPlayer.setModified(true);
                    storageModule.addPlayerData(newPlayer);
                    PermissibleInjector.injectPlayer(plugin, player);
                } catch (NullPointerException ex2) {
                    Bukkit.getLogger().severe(ex2.getMessage());
                }
                return;
            }
            loadedPlayer.setModified(false);
            storageModule.addPlayerData(loadedPlayer);
            PermissibleInjector.injectPlayer(plugin, player);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PlayerData playerData = playerModule.getPlayerData(player);
        storageModule.removePlayerData(playerData);
        if (playerData == null || !playerData.isModified()) {
            return;
        }

        this.dataStorage.save(playerData).exceptionally(ex -> {
            Bukkit.getLogger().info(ex.getMessage());
            return null;
        });

    }
}
