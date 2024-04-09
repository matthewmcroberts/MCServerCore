package com.matthew.template.modules.ranks;

import com.matthew.template.api.ServerModule;
import com.matthew.template.data.PlayerData;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.ranks.command.RankCommand;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.ranks.structure.RankType;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RankModule implements ServerModule {

    private final JavaPlugin plugin;

    private final ServerModuleManager moduleManager;
    private final DataStorageModule storage;
    private final Set<Rank> ranks;

    public RankModule(JavaPlugin plugin) {
        this.plugin = plugin;
        this.moduleManager = ServerModuleManager.getInstance();
        this.storage = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.ranks = new HashSet<>();
    }

    public Set<Rank> getRanks() {
        assert storage != null;
        return storage.getAllRanks();
    }

    //TODO: Get a set of all online players (meaning already in cache) that have this rank
    public Set<PlayerData> getWhoOnline(RankType rank) {
        return null;
    }

    //TODO: implement logic
    public boolean hasRank(Player player, RankType rank) {
        return false;
    }

    //TODO: implement logic
    public Rank getRank(Player player) {
        return null;
    }

    @Override
    public void setUp() {
        for (Rank rank : ranks) {
            assert storage != null;
            storage.addRank(rank);
        }
        CommandExecutor rankCommand = new RankCommand();
        Objects.requireNonNull(plugin.getCommand("rank")).setExecutor(rankCommand);
    }

    @Override
    public void teardown() {
    }
}
