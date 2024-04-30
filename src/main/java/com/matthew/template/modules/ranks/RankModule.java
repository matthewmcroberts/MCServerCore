package com.matthew.template.modules.ranks;

import com.matthew.template.api.ServerModule;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.ranks.command.RankCommand;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Set;

public class RankModule implements ServerModule {

    private final JavaPlugin plugin;

    private DataStorageModule storageModule;

    public RankModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Set<Rank> getRanks() {
        return storageModule.getAllRanks();
    }

    //TODO: Get a set of all online players (meaning already in cache) that have this rank
    public Set<PlayerData> getWhoOnline(String rank) {
        return null;
    }

    //TODO: implement logic
    public boolean hasRank(Player player, String rank) {
        return false;
    }

    //TODO: implement logic
    public Rank getRank(Player player) {
        return null;
    }

    public Rank getRank(String name) {
        for(Rank rank: storageModule.getAllRanks()) {
            if(rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public Rank getDefaultRank() {
        for (Rank rank : getRanks()) {
            if (rank.isDefault()) {
                return rank;
            }
        }
        throw new NullPointerException("Default rank cannot be null in ranks.yml");
    }

    @Override
    public void setUp() {
        this.storageModule = ServerModuleManager.getInstance().getRegisteredModule(DataStorageModule.class);
        CommandExecutor rankCommand = new RankCommand(plugin);
        Objects.requireNonNull(plugin.getCommand("rank")).setExecutor(rankCommand);
    }

    @Override
    public void teardown() {
    }
}
