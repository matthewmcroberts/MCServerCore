package com.matthew.template.bukkit.events.listeners;

import com.matthew.template.bukkit.ServerCore;
import com.matthew.template.common.apis.DataStorage;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.bukkit.permissions.PermissibleInjector;
import com.matthew.template.common.modules.player.dto.PlayerDTO;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.ranks.dto.RankDTO;
import com.matthew.template.common.modules.storage.DataStorageModule;
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
                    RankDTO defaultRankDTO = rankModule.getDefaultRank();
                    PlayerDTO newPlayer = new PlayerDTO(player, defaultRankDTO, 0L);
                    newPlayer.setModified(true);
                    storageModule.addPlayerData(newPlayer);
                } catch (NullPointerException ex2) {
                    Bukkit.getLogger().severe(ex2.getMessage());
                }
            } else {
                loadedPlayer.setModified(false);
                storageModule.addPlayerData(loadedPlayer);
            }

            PermissibleInjector.injectPlayer(plugin, player);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PlayerDTO playerDto = playerModule.getPlayerData(player);
        storageModule.removePlayerData(playerDto);
        if (playerDto == null || !playerDto.isModified()) {
            return;
        }

        this.dataStorage.save(playerDto).exceptionally(ex -> {
            Bukkit.getLogger().info(ex.getMessage());
            return null;
        });

    }
}
