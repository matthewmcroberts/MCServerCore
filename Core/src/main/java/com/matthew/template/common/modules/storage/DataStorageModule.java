package com.matthew.template.common.modules.storage;

import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.player.dto.PlayerDTO;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.ranks.dto.RankDTO;
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
     * @param rankDTO The Rank object to add.
     * @return True if the rank was successfully added, false otherwise.
     */
    public boolean addRank(RankDTO rankDTO) {
        if ((containsRank(rankDTO))) {
            return false;
        }
        cache.getRanks().add(rankDTO);
        return true;
    }

    /**
     * Removes a rank from cache.
     *
     * @param rankDTO The Rank object to remove.
     * @return True if the rank was successfully removed, false otherwise.
     */
    public boolean removeRank(RankDTO rankDTO) {
        if (!(containsRank(rankDTO))) {
            return false;
        }
        cache.getRanks().remove(rankDTO);
        return true;
    }

    public RankDTO getRank(String rankName) {
        for(RankDTO rankDTO : cache.getRanks()) {
            if(rankDTO.getName().equalsIgnoreCase(rankName)) {
                return rankDTO;
            }
        }
        return null;
    }

    public RankDTO getRank(Player player) {
        if(isLoaded(player)) {
            return Objects.requireNonNull(getPlayerData(player)).getRankDTO();
        }
        return null;
    }

    /**
     * Retrieves all ranks stored in cache.
     *
     * @return An unmodifiable set of all ranks.
     */
    public Set<RankDTO> getAllRanks() {
        return Collections.unmodifiableSet(cache.getRanks());
    }

    /**
     * Adds a player's data to cache.
     *
     * @param player The PlayerData object to add.
     * @return True if the player's data was successfully added, false otherwise.
     */
    public boolean addPlayerData(PlayerDTO player) {
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
    public boolean removePlayerData(PlayerDTO player) {
        if(!(containsPlayer(player))) {
            return false;
        }
        cache.getPlayers().remove(player);
        return true;
    }

    public PlayerDTO getPlayerData(Player player) {
        for(PlayerDTO playerDto : cache.getPlayers()) {
            if(playerDto.getName().equals(player.getName())) {
                return playerDto;
            }
        }
        return null;
    }

    public PlayerDTO getPlayerData(String playerName) {
        for(PlayerDTO playerDto : cache.getPlayers()) {
            if(playerDto.getName().equals(playerName)) {
                return playerDto;
            }
        }
        return null;
    }

    /**
     * Retrieves all player data stored in the module.
     *
     * @return An unmodifiable list of all player data.
     */
    public List<PlayerDTO> getAllPlayerData() {
        return Collections.unmodifiableList(cache.getPlayers());
    }

    public PlayerDTO createPlayerData(Player player) {
        RankDTO defaultRankDTO = rankModule.getDefaultRank();
        PlayerDTO newPlayer = new PlayerDTO(player, defaultRankDTO, 0L);
        newPlayer.setModified(true);
        addPlayerData(newPlayer);
        return newPlayer;
    }

    /**
     * Checks if a rank is contained in cache.
     *
     * @param rankDTO The Rank object to check.
     * @return True if the rank is contained, false otherwise.
     */
    public boolean containsRank(RankDTO rankDTO) {
        return cache.getRanks().contains(rankDTO);
    }

    /**
     * Checks if a player's data is contained in cache.
     *
     * @param playerDto The PlayerData object to check.
     * @return True if the player's data is contained, false otherwise.
     */
    public boolean containsPlayer(PlayerDTO playerDto) {
        return cache.getPlayers().contains(playerDto);
    }

    public boolean isLoaded(Player player) {
        for(PlayerDTO playerDto : cache.getPlayers()) {
            if(playerDto.getName().equals(player.getName())) {
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
        private final Set<RankDTO> rankDTOS;
        private final List<PlayerDTO> players;

        /**
         * Constructs a new Cache with an empty set and list.
         */
        public Cache() {
            this.rankDTOS = new HashSet<>();
            this.players = new ArrayList<>();
        }

        /**
         * Retrieves the set of ranks.
         *
         * @return The set of ranks.
         */
        public Set<RankDTO> getRanks() {
            return rankDTOS;
        }

        /**
         * Retrieves the list of player data.
         *
         * @return The list of player data.
         */
        public List<PlayerDTO> getPlayers() {
            return players;
        }

        /**
         * Clears all stored data.
         */
        public void clear() {
            rankDTOS.clear();
            players.clear();
        }
    }
}