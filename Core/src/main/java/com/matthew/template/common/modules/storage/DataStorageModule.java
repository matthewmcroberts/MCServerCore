package com.matthew.template.common.modules.storage;

import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.player.structure.PlayerData;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.ranks.structure.Rank;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * The DataStorageModule class handles the storage of ranks and player data for the plugin.
 * It provides methods to add, remove, and retrieve ranks and player data. For additional usage with data stored in
 * cache, the other modules are to be used.
 */
public final class DataStorageModule implements ServerModule {

    /*
     * The JavaPlugin the ServerModule is created from
     */
    private JavaPlugin plugin;

    /*
     * The cache storing all necessary data related to the players and ranks
     */
    private final Cache cache;

    private RankModule rankModule;

    /**
     * Constructs a new DataStorageModule with the given JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance associated with this module.
     */
    public DataStorageModule(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cache = new Cache();
    }

    /**
     * Adds a new rank to cache.
     *
     * @param rank The Rank object to add.
     * @return True if the rank was successfully added, false otherwise.
     */
    public boolean addRank(Rank rank) {
        if ((containsRank(rank))) {
            return false;
        }
        cache.getRanks().add(rank);
        return true;
    }

    /**
     * Removes a rank from cache.
     *
     * @param rank The Rank object to remove.
     * @return True if the rank was successfully removed, false otherwise.
     */
    public boolean removeRank(Rank rank) {
        if (!(containsRank(rank))) {
            return false;
        }
        cache.getRanks().remove(rank);
        return true;
    }

    public Rank getRank(String rankName) {
        for(Rank rank: cache.getRanks()) {
            if(rank.getName().equalsIgnoreCase(rankName)) {
                return rank;
            }
        }
        return null;
    }

    public Rank getRank(Player player) {
        if(isLoaded(player)) {
            return Objects.requireNonNull(getPlayerData(player)).getRank();
        }
        return null;
    }

    /**
     * Retrieves all ranks stored in cache.
     *
     * @return An unmodifiable set of all ranks.
     */
    public Set<Rank> getAllRanks() {
        return Collections.unmodifiableSet(cache.getRanks());
    }

    /**
     * Adds a player's data to cache.
     *
     * @param player The PlayerData object to add.
     * @return True if the player's data was successfully added, false otherwise.
     */
    public boolean addPlayerData(PlayerData player) {
        if ((containsPlayer(player))) {
            return false;
        }
        cache.getPlayers().add(player);
        return true;
    }

    /**
     * Removes a player's data from cache.
     *
     * @param player The PlayerData object to remove.
     * @return True if the player's data was successfully removed, false otherwise.
     */
    public boolean removePlayerData(PlayerData player) {
        if(!(containsPlayer(player))) {
            return false;
        }
        cache.getPlayers().remove(player);
        return true;
    }

    public PlayerData getPlayerData(Player player) {
        for(PlayerData playerData: cache.getPlayers()) {
            if(playerData.getName().equals(player.getName())) {
                return playerData;
            }
        }
        return null;
    }

    public PlayerData getPlayerData(String playerName) {
        for(PlayerData playerData: cache.getPlayers()) {
            if(playerData.getName().equals(playerName)) {
                return playerData;
            }
        }
        return null;
    }

    /**
     * Retrieves all player data stored in the module.
     *
     * @return An unmodifiable list of all player data.
     */
    public List<PlayerData> getAllPlayerData() {
        return Collections.unmodifiableList(cache.getPlayers());
    }

    public PlayerData createPlayerData(Player player) {
        Rank defaultRank = rankModule.getDefaultRank();
        PlayerData newPlayer = new PlayerData(player, defaultRank, 0L);
        newPlayer.setModified(true);
        addPlayerData(newPlayer);
        return newPlayer;
    }

    /**
     * Checks if a rank is contained in cache.
     *
     * @param rank The Rank object to check.
     * @return True if the rank is contained, false otherwise.
     */
    public boolean containsRank(Rank rank) {
        return cache.getRanks().contains(rank);
    }

    /**
     * Checks if a player's data is contained in cache.
     *
     * @param playerData The PlayerData object to check.
     * @return True if the player's data is contained, false otherwise.
     */
    public boolean containsPlayer(PlayerData playerData) {
        return cache.getPlayers().contains(playerData);
    }

    public boolean isLoaded(Player player) {
        for(PlayerData playerData: cache.getPlayers()) {
            if(playerData.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets up the module.
     */
    @Override
    public void setUp() {
        this.rankModule = ServerModuleManager.getInstance().getRegisteredModule(RankModule.class);
    }

    /**
     * Tears down any additional allocated resources
     */
    @Override
    public void teardown() {
        cache.clear();
    }

    /**
     * The Cache class is an internal class for storing ranks and player data. The purpose is to provide a tight
     * coupling between the Cache and DataStorageModule
     */
    private static class Cache {
        private final Set<Rank> ranks;
        private final List<PlayerData> players;

        /**
         * Constructs a new Cache with an empty set and list.
         */
        public Cache() {
            this.ranks = new HashSet<>();
            this.players = new ArrayList<>();
        }

        /**
         * Retrieves the set of ranks.
         *
         * @return The set of ranks.
         */
        public Set<Rank> getRanks() {
            return ranks;
        }

        /**
         * Retrieves the list of player data.
         *
         * @return The list of player data.
         */
        public List<PlayerData> getPlayers() {
            return players;
        }

        /**
         * Clears all stored data.
         */
        public void clear() {
            ranks.clear();
            players.clear();
        }
    }
}