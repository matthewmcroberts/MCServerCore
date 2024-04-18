package com.matthew.template.data.db.framework;

import com.matthew.template.api.DataStorage;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.storage.DataStorageModule;
import com.matthew.template.serializer.Serializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class SQLDataStorage implements DataStorage {


    private final Serializer serializer;

    private final DataStorageModule module;

    public SQLDataStorage() {
        final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.serializer = new Serializer();
    }

    @NotNull
    @Override
    public CompletableFuture<Void> init() {
        return null;
    }

    @NotNull
    @Override
    public CompletableFuture<PlayerData> load(@NotNull PlayerData player) {
        return null;

    }

    @NotNull
    @Override
    public CompletableFuture<PlayerData> save(@NotNull PlayerData player) {
        return null;

    }

    @NotNull
    @Override
    public CompletableFuture<List<PlayerData>> save(@NotNull Collection<? extends PlayerData> players) {
        return null;

    }


    protected abstract void initDriver() throws ClassNotFoundException;

    protected abstract Connection openConnection() throws SQLException;

    @NotNull
    protected abstract String getCreatePlayersTableStatement();

    @NotNull
    protected abstract String getInsertPlayerDataStatement();

    @NotNull
    protected abstract String getSelectAllPlayerDataQuery();

}
