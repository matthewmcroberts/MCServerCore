package com.matthew.template.modules.storage;

import com.matthew.template.api.ServerModule;
import com.matthew.template.data.PlayerData;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.ranks.structure.RankType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/*
 The DataStorageModule's purpose is for getting and adding data directly stored in the Cache
 For additional functionality with the data, the other modules will be accessed
 */
public final class DataStorageModule implements ServerModule {

    private JavaPlugin plugin;
    private final Cache cache = new Cache();

    public DataStorageModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean addRank(Rank rank) {
        if (!(exists(rank))) {
            return false;
        }
        cache.getRanks().add(rank);
        return true;
    }

    public boolean removeRank(Rank rank) {
        if (!(exists(rank))) {
            return false;
        }
        cache.getRanks().remove(rank);
        return true;
    }


    public Set<Rank> getAllRanks() {
        return cache.getRanks();
    }

    public boolean addPlayer(PlayerData player) {
        if (!(exists(player))) {
            return false;
        }
        cache.getPlayers().add(player);
        return true;
    }

    public boolean removePlayer(PlayerData player) {
        if(!(exists(player))) {
            return false;
        }
        cache.getPlayers().remove(player);
        return true;
    }

    public List<PlayerData> getAllPlayerData() {
        return cache.getPlayers();
    }

    private boolean exists(Rank rank) {
        return cache.getRanks().contains(rank);
    }

    private boolean exists(PlayerData playerData) {
        return cache.getPlayers().contains(playerData);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void teardown() {

    }

    private static class Cache {
        private final Set<Rank> ranks = new HashSet<>();
        private final List<PlayerData> players = new ArrayList<>();

        public Set<Rank> getRanks() {
            return ranks;
        }

        public List<PlayerData> getPlayers() {
            return players;
        }
    }
}
