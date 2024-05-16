package com.matthew.template.common.data.config.framework;

import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.storage.DataStorageModule;

import java.io.IOException;

public abstract class ConfigManager {

    protected final ServerModuleManager moduleManager;

    protected final DataStorageModule module;

    public ConfigManager() {
        this.moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(DataStorageModule.class);
    }

    public abstract void save();

    public abstract void reload() throws IOException;

    public abstract void destroy();

}
