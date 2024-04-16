package com.matthew.template.data.db.framework;

import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.storage.DataStorageModule;
import com.matthew.template.serializer.Serializer;
import org.bukkit.entity.Player;

public abstract class StorageManager {


    private final Serializer serializer;

    private final DataStorageModule module;

    public StorageManager() {
        final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.serializer = new Serializer();
    }

    public DataStorageModule getStorageModule() {
        return this.module;
    }

    public Serializer getSerializer() {
        return this.serializer;
    }

    public abstract void loadPlayer(Player player);

    public abstract void loadAllPlayers();

    public abstract void savePlayer(Player player);

    public abstract void saveAllPlayers();

    



}
