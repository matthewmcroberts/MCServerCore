package com.matthew.template.data.db.framework;

import com.matthew.template.api.DataStorage;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.PlayerModule;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.storage.DataStorageModule;
import com.matthew.template.serializer.Serializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class SQLDataStorage implements DataStorage {


    private final Serializer serializer;
    private final DataStorageModule storageModule;
    private final PlayerModule playerModule;

    public SQLDataStorage() {
        final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
        this.storageModule = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.playerModule = moduleManager.getRegisteredModule(PlayerModule.class);
        this.serializer = new Serializer();
    }

    @NotNull
    @Override
    public CompletableFuture<Void> init() {
        return CompletableFuture.runAsync(() -> {
            try {
                initDriver();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to load player data", e);
            }
        }).thenRun(() -> {
            try (Connection connection = openConnection()) {
                connection.createStatement().execute(getCreatePlayersTableStatement());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @NotNull
    @Override
    public CompletableFuture<PlayerData> load(Player player) {
        if(playerModule.isLoaded(player)) { //player for some reason is already loaded in the cache
            return CompletableFuture.completedFuture(playerModule.getPlayerData(player));
        }

        return CompletableFuture.supplyAsync(() -> {
            try(Connection connection = openConnection()) {
                PreparedStatement statement = connection.prepareStatement(getSelectPlayerDataQuery());

                ResultSet result = statement.executeQuery();

                return (result.next()) ? this.handleResult(result) : null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @NotNull
    @Override
    public CompletableFuture<List<PlayerData>> load() {
        return CompletableFuture.supplyAsync(() -> {
            try(Connection connection = openConnection()) {
                PreparedStatement statement = connection.prepareStatement(getSelectAllPlayerDataQuery());

                ResultSet result = statement.executeQuery();
                if(result.next()) {

                }
            } catch(SQLException e) {
                throw new RuntimeException("Failed to load player data", e);
            }
            return null;
        });

    }

    @NotNull
    @Override
    public CompletableFuture<PlayerData> save(@NotNull String playerUUID) {
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

    @NotNull
    protected abstract String getSelectPlayerDataQuery();


    private PlayerData handleResult(ResultSet results) throws SQLException {
        String json = results.getString("data");

        if(json == null) {
            return null;
        }

        PlayerData newPlayer = serializer.deserializeFromJsonString(json, PlayerData.class);
        storageModule.addPlayer(newPlayer);
        return newPlayer;
    }

    private List<PlayerData> handleResults(ResultSet results) {
        return null;
    }

}
