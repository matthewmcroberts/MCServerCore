package com.matthew.template;

import com.matthew.template.generalcommands.GeneralCommandManager;
import com.matthew.template.generalevents.GeneralEventsManager;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.permissions.PermissionsModule;
import org.bukkit.plugin.java.JavaPlugin;


public final class ServerCore extends JavaPlugin {

    private ServerModuleManager moduleManager;
    private GeneralEventsManager mechanicManager;
    private GeneralCommandManager commandManager;


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
        moduleManager.registerModule(new PermissionsModule(this));
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
