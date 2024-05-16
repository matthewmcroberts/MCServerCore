package com.matthew.template.common.apis;

import com.matthew.template.common.modules.player.structure.PlayerData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DataStorage {

    @NotNull
    public CompletableFuture<Void> init();

    @NotNull
    public CompletableFuture<PlayerData> load(Player player);

    @NotNull
    public CompletableFuture<List<PlayerData>> load();

    @NotNull
    public CompletableFuture<PlayerData> save(@NotNull PlayerData player);

    @NotNull
    public CompletableFuture<List<PlayerData>> save(@NotNull Collection<? extends PlayerData> players);

}