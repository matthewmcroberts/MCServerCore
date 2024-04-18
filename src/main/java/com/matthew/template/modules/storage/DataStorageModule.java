package com.matthew.template.modules.storage;

import com.matthew.template.api.ServerModule;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.entity.Player;
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
        if ((contains(rank))) {
            return false;
        }
        cache.getRanks().add(rank);
        return true;
    }

    public boolean removeRank(Rank rank) {
        if (!(contains(rank))) {
            return false;
        }
        cache.getRanks().remove(rank);
        return true;
    }
    //TODO: Return a copy of set instead
    public Set<Rank> getAllRanks() {
        return cache.getRanks();
    }


    public boolean addPlayer(PlayerData player) {
        if ((contains(player))) {
            return false;
        }
        cache.getPlayers().add(player);
        return true;
    }

    public boolean removePlayer(PlayerData player) {
        if(!(contains(player))) {
            return false;
        }
        cache.getPlayers().remove(player);
        return true;
    }

    //TODO: Return a copy of list instead
    public List<PlayerData> getAllPlayerData() {
        return cache.getPlayers();
    }

    public boolean contains(Rank rank) {
        return cache.getRanks().contains(rank);
    }

    public boolean contains(PlayerData playerData) {
        return cache.getPlayers().contains(playerData);
    }

    private void clearRanks() {
        cache.getRanks().clear();
    }

    private void clearPlayers() { cache.getPlayers().clear(); }

    private boolean contains(Player playerData) {
        for(PlayerData player: cache.getPlayers()) {
            if(player.getName().equals(playerData.getName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void setUp() {

    }

    @Override
    public void teardown() {
        clearRanks();
        clearPlayers();
    }

    private static class Cache {
        private final Set<Rank> ranks;
        private final List<PlayerData> players;

        public Cache() {
            this.ranks = new HashSet<>();
            this.players = new ArrayList<>();
        }

        public Set<Rank> getRanks() {
            return ranks;
        }

        public List<PlayerData> getPlayers() {
            return players;
        }
    }
}
