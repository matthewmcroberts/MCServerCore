package com.matthew.template.common.modules.ranks;

import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.player.dto.PlayerDTO;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.ranks.dto.RankDTO;
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

    public Set<RankDTO> getRanks() {
        return storageModule.getAllRanks();
    }

    public List<PlayerDTO> getWhoOnline(String rank) {
        List<PlayerDTO> players = new ArrayList<>();
        for(Player player: Bukkit.getOnlinePlayers()) {
            PlayerDTO playerDto = playerModule.getPlayerData(player);
            if(playerDto != null && playerDto.getRankDTO().getName().equalsIgnoreCase(rank)) {
                players.add(playerDto);
            }
        }
        return players;
    }

    public RankDTO getRank(Player player) {
        return storageModule.getRank(player);
    }

    public RankDTO getRank(String name) {
        return storageModule.getRank(name);
    }

    public RankDTO getDefaultRank() {
        for (RankDTO rankDTO : getRanks()) {
            if (rankDTO.isDefault()) {
                return rankDTO;
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
