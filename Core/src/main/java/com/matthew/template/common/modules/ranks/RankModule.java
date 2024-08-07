package com.matthew.template.common.modules.ranks;

import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.player.data.PlayerData;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.ranks.data.RankData;
import com.matthew.template.common.modules.storage.DataStorageModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RankModule implements ServerModule {

    private final JavaPlugin plugin;

    private DataStorageModule storageModule;

    private PlayerModule playerModule;

    public RankModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Set<RankData> getRanks() {
        return storageModule.getAllRanks();
    }

    public List<PlayerData> getWhoOnline(String rank) {
        List<PlayerData> players = new ArrayList<>();
        for(Player player: Bukkit.getOnlinePlayers()) {
            PlayerData playerData = playerModule.getPlayerData(player);
            if(playerData != null && playerData.getRankData().getName().equalsIgnoreCase(rank)) {
                players.add(playerData);
            }
        }
        return players;
    }

    public RankData getRank(Player player) {
        return storageModule.getRank(player);
    }

    public RankData getRank(String name) {
        return storageModule.getRank(name);
    }

    public RankData getDefaultRank() {
        for (RankData rankData : getRanks()) {
            if (rankData.isDefault()) {
                return rankData;
            }
        }
        throw new NullPointerException("Default rank cannot be null in ranks.yml");
    }

    @Override
    public void setUp() {
        this.storageModule = ServerModuleManager.getInstance().getRegisteredModule(DataStorageModule.class);
        this.playerModule = ServerModuleManager.getInstance().getRegisteredModule(PlayerModule.class);
    }

    @Override
    public void teardown() {
    }
}
