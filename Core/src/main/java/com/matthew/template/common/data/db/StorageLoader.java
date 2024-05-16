package com.matthew.template.common.data.db;

import com.matthew.template.common.data.db.config.MySQLConfig;

public class StorageLoader {

    private final MySQLConfig sqlConfiguration;

    public StorageLoader(MySQLConfig sqlConfiguration) {
        this.sqlConfiguration = sqlConfiguration;
    }

    public MySQLDataStorage getStorageManager() {
        return new MySQLDataStorage(sqlConfiguration);
    }

    public void loadData(MySQLDataStorage storageManager) {
        if(storageManager == null) {
            throw new IllegalArgumentException("Storage Manager must not be null");
        }
        //storageManager.loadAllPlayers();
    }

    public void saveAllData(MySQLDataStorage storageManager) {
        //storageManager.saveAllPlayers();
    }
}
