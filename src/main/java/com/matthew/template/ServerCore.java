package com.matthew.template;

import com.matthew.template.api.DataStorage;
import com.matthew.template.data.config.RankConfigManager;
import com.matthew.template.data.db.MySQLDataStorage;
import com.matthew.template.data.db.config.MySQLConfig;
import com.matthew.template.generalcommands.GeneralCommandManager;
import com.matthew.template.generalevents.GeneralEventsManager;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.PlayerModule;
import com.matthew.template.modules.ranks.RankModule;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public final class ServerCore extends JavaPlugin {

    private static ServerCore instance;
    private DataStorage dataStorage;

    private ServerModuleManager moduleManager;
    private GeneralEventsManager mechanicManager;
    private GeneralCommandManager commandManager;

    private RankConfigManager rankConfig;

    //open database connection and load necessary data into cache
    @Override
    public void onEnable() {
        instance = this;

        //General Events Setup
        mechanicManager = new GeneralEventsManager(this);
        mechanicManager.register();

        //General Commands Setup
        commandManager = new GeneralCommandManager(this);
        commandManager.register();

        //Modules Setup
        moduleManager = ServerModuleManager.getInstance();
        moduleManager.registerModule(new DataStorageModule(this))
                .registerModule(new RankModule(this))
                .registerModule(new PlayerModule(this));

        //Load Ranks into cache
        try {
            rankConfig = new RankConfigManager(this);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        //Load MySQL Data
        MySQLConfig sqlConfig = new MySQLConfig();
        sqlConfig.setDatabase("testserver");
        sqlConfig.setHost("127.0.0.1");
        sqlConfig.setPort(3306);
        sqlConfig.setUsername("root");
        sqlConfig.setPassword("");
        dataStorage = new MySQLDataStorage(sqlConfig);
        dataStorage.init();

        moduleManager.setUp();


        getLogger().info(moduleManager.getRegisteredModules().toString());
        getLogger().info("ServerCore loaded");
    }

    @Override
    public void onDisable() {
        rankConfig.save();

        moduleManager.teardown();
        mechanicManager.unregister();
        commandManager.unregister();
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }

    public static ServerCore getInstance() {
        return instance;
    }
}
