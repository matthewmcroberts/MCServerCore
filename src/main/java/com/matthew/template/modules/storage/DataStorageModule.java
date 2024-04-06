package com.matthew.template.modules.storage;

import com.matthew.template.api.ServerModule;
import com.matthew.template.data.PlayerData;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.ranks.structure.RankType;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class DataStorageModule implements ServerModule {

    private JavaPlugin plugin;
    private final Cache cache = new Cache();

    public DataStorageModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addRank(Rank rank) {
        cache.addRank(rank);
    }

    public Rank getRank(RankType rankType) {
        return cache.getRank(rankType);
    }

    public Set<Rank> getRanks() {
        return cache.getRanks();
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

        public void addRank(Rank rank) {
            ranks.add(rank);
        }

        public Rank getRank(RankType rankType) {
            for (Rank rank : ranks) {
                if (rank.getType() == rankType) {
                    return rank;
                }
            }
            return null;
        }

        public Set<Rank> getRanks() {
            return ranks;
        }
    }
}
