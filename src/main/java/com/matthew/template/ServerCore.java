package com.matthew.template;

import com.matthew.template.generalcommands.GeneralCommandManager;
import com.matthew.template.generalevents.GeneralEventsManager;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.ranks.RankModule;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.plugin.java.JavaPlugin;


public final class ServerCore extends JavaPlugin {

    private ServerModuleManager moduleManager;
    private GeneralEventsManager mechanicManager;
    private GeneralCommandManager commandManager;

    //open database connection and load necessary data into cache
    @Override
    public void onEnable() {

        //General Events Setup
        mechanicManager = new GeneralEventsManager(this);
        mechanicManager.register();

        //General Commands Setup
        commandManager = new GeneralCommandManager(this);
        commandManager.register();

        //Modules Setup
        moduleManager = ServerModuleManager.getInstance();
        moduleManager.registerModule(new DataStorageModule(this));
        moduleManager.registerModule(new RankModule(this));
        moduleManager.setUp();

        getLogger().info("NativePractice loaded");
    }

    @Override
    public void onDisable() {
        moduleManager.teardown();
        mechanicManager.unregister();
        commandManager.unregister();
    }
}
