package com.matthew.template.data.db;

import com.matthew.template.data.db.config.MySQLConfig;
import com.matthew.template.data.db.framework.StorageManager;
import org.bukkit.entity.Player;

import java.sql.Connection;

public class MySQLStorageManager extends StorageManager {

    private Connection connection;
    private final MySQLConfig sqlConfig;

    public MySQLStorageManager(MySQLConfig config) {
        super();
        this.sqlConfig = config;
    }

    @Override
    public void loadPlayer(Player player) {

    }

    @Override
    public void loadAllPlayers() {

    }

    @Override
    public void savePlayer(Player player) {

    }

    @Override
    public void saveAllPlayers() {

    }
}
