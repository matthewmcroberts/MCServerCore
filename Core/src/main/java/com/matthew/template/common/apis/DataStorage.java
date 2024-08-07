package com.matthew.template.common.apis;

import com.matthew.template.common.modules.player.dto.PlayerDTO;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DataStorage {

    /**
     * Initializes the data storage.
     * This method should be called before any other operations are performed on the data storage.
     *
     * @return a CompletableFuture that completes when the initialization is done.
     */
    @NotNull
    public CompletableFuture<Void> init();

    /**
     * Loads the player data for a specific player.
     *
     * @param player the player whose data needs to be loaded.
     * @return a CompletableFuture that completes with the loaded PlayerData.
     */
    @NotNull
    public CompletableFuture<PlayerDTO> load(Player player);

    /**
     * Loads the player data for all players.
     *
     * @return a CompletableFuture that completes with a list of all loaded PlayerData.
     */
    @NotNull
    public CompletableFuture<List<PlayerDTO>> load();

    /**
     * Saves the data for a specific player.
     *
     * @param player the PlayerData to be saved.
     * @return a CompletableFuture that completes with the saved PlayerData.
     */
    @NotNull
    public CompletableFuture<PlayerDTO> save(@NotNull PlayerDTO player);

    /**
     * Saves the data for a collection of players.
     *
     * @param players the collection of PlayerData to be saved.
     * @return a CompletableFuture that completes with a list of all saved PlayerData.
     */
    @NotNull
    public CompletableFuture<List<PlayerDTO>> save(@NotNull Collection<? extends PlayerDTO> players);

}
