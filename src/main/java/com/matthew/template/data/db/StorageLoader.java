package com.matthew.template.data.db;

import com.matthew.template.data.db.config.MySQLConfig;

public class StorageLoader {

    private final MySQLConfig sqlConfiguration;

    public StorageLoader(MySQLConfig sqlConfiguration) {
        this.sqlConfiguration = sqlConfiguration;
    }

    public MySQLStorageManager getStorageManager() {
        return new MySQLStorageManager(sqlConfiguration);
    }

    public void loadData(MySQLStorageManager storageManager) {
        if(storageManager == null) {
            throw new IllegalArgumentException("Storage Manager must not be null");
        }
        storageManager.loadAllPlayers();
    }

    public void saveAllData(MySQLStorageManager storageManager) {
        storageManager.saveAllPlayers();
    }
}
