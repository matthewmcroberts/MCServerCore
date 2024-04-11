package com.matthew.template.data.config.framework;

import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class ConfigManager {

    protected final ServerModuleManager moduleManager;

    protected final DataStorageModule module;

    public ConfigManager() {
        this.moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(DataStorageModule.class);
    }

    public abstract void save();

    public abstract void reload();

    public abstract void destroy();

    //implement logic for copyFile, isNewFile, getKeys, hasKey, setString, hasString, getInt, setInt, hasBool, setBool, etc...
}
