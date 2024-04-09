package com.matthew.template.data.config;

import com.matthew.template.data.config.framework.ConfigManager;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class RankConfigManager extends ConfigManager {

    private final JavaPlugin plugin;

    private final ServerModuleManager moduleManager;

    private final DataStorageModule module;

    private final String path;

    //Use constructor to load file
    public RankConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.path = "ranks.yml";

        plugin.saveResource(path, false);

        File rankFile = new File(plugin.getDataFolder(), path);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(rankFile);

    }

    @Override
    public void save() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void destroy() {

    }
}
