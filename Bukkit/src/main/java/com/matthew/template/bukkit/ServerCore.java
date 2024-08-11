package com.matthew.template.bukkit;

import com.matthew.template.bukkit.modules.commands.CommandModule;
import com.matthew.template.bukkit.modules.messages.MessageModule;
import com.matthew.template.common.apis.DataStorage;
import com.matthew.template.common.data.config.RankConfigManager;
import com.matthew.template.common.data.db.MySQLDataStorage;
import com.matthew.template.common.data.db.config.MySQLConfig;
import com.matthew.template.bukkit.events.GeneralEventsManager;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.storage.DataStorageModule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public final class ServerCore extends JavaPlugin {

    private static ServerCore instance;

    private DataStorage dataStorage;

    private ServerModuleManager moduleManager;

    private GeneralEventsManager mechanicManager;

    private RankConfigManager rankConfig;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        //Register Modules
        Bukkit.getLogger().info("Registering modules...");
        moduleManager = ServerModuleManager.getInstance();
        moduleManager.registerModule(new DataStorageModule(this))
                .registerModule(new RankModule(this))
                .registerModule(new PlayerModule(this))
                .registerModule(new CommandModule(this))
                .registerModule(new MessageModule(this));

        //Load Ranks into cache
        Bukkit.getLogger().info("Attempting to load ranks from ranks.yml into cache...");
        try {
            rankConfig = new RankConfigManager(this);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        //Load MySQL Data
        Bukkit.getLogger().info("Loading MySQL database...");
        MySQLConfig sqlConfig = new MySQLConfig();
        sqlConfig.setDatabase("testserver");
        sqlConfig.setHost("127.0.0.1");
        sqlConfig.setPort(3306);
        sqlConfig.setUsername("root");
        sqlConfig.setPassword("");
        dataStorage = new MySQLDataStorage(sqlConfig);
        dataStorage.init();

        //Setup modules
        Bukkit.getLogger().info("Setting up modules...");
        moduleManager.setUp();

        //General Events Setup
        Bukkit.getLogger().info("Registering events...");
        mechanicManager = new GeneralEventsManager(this);
        mechanicManager.register();

        Bukkit.getLogger().info("ServerCore successfully enabled!");
    }

    @Override
    public void onDisable() {
        rankConfig.save();

        moduleManager.teardown();
        mechanicManager.unregister();
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }

    public static ServerCore getInstance() {
        return instance;
    }
}
