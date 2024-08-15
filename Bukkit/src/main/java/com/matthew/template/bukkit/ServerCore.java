package com.matthew.template.bukkit;

import com.matthew.template.bukkit.events.listeners.PlayerDataListener;
import com.matthew.template.bukkit.modules.chat.ChatModule;
import com.matthew.template.bukkit.modules.commands.CommandModule;
import com.matthew.template.bukkit.modules.messages.MessageModule;
import com.matthew.template.common.apis.DataStorage;
import com.matthew.template.common.data.config.RankConfigManager;
import com.matthew.template.common.data.db.MySQLDataStorage;
import com.matthew.template.common.data.db.config.MySQLConfig;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.storage.DataStorageModule;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public final class ServerCore extends JavaPlugin {

    @Getter
    private static ServerCore instance;

    @Getter
    private DataStorage dataStorage;

    private ServerModuleManager moduleManager;

    private RankConfigManager rankConfig;

    private MySQLConfig sqlConfig;

    @Override
    public void onLoad() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        Bukkit.getLogger().info("Loading parameters for MySQL database...");
        sqlConfig = new MySQLConfig();
        sqlConfig.setDatabase("testserver");
        sqlConfig.setHost("127.0.0.1");
        sqlConfig.setPort(3306);
        sqlConfig.setUsername("root");
        sqlConfig.setPassword("");
    }

    @Override
    public void onEnable() {
        instance = this;

        //Register Modules
        Bukkit.getLogger().info("Registering modules...");
        moduleManager = ServerModuleManager.getInstance();
        moduleManager.registerModule(new DataStorageModule(this))
                .registerModule(new RankModule(this))
                .registerModule(new PlayerModule(this))
                .registerModule(new CommandModule(this))
                .registerModule(new MessageModule(this))
                .registerModule(new ChatModule(this));

        //Load Ranks into cache
        Bukkit.getLogger().info("Attempting to load ranks from ranks.yml into cache...");
        try {
            rankConfig = new RankConfigManager(this);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        //Load MySQL Data
        Bukkit.getLogger().info("Initializing MySQL database...");
        dataStorage = new MySQLDataStorage(sqlConfig);
        dataStorage.init();

        //Setup modules
        Bukkit.getLogger().info("Setting up modules...");
        moduleManager.setUp();

        //General Events Setup
        Bukkit.getLogger().info("Registering events...");
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this), this);

        Bukkit.getLogger().info("ServerCore successfully enabled!");
    }

    @Override
    public void onDisable() {
        rankConfig.save();

        moduleManager.teardown();
    }
}
