package com.matthew.template.modules.player.listeners;

import com.matthew.template.ServerCore;
import com.matthew.template.api.DataStorage;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.PlayerModule;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {

    private final JavaPlugin plugin;

    private final DataStorage dataStorage;

    private final DataStorageModule storageModule;
    private final PlayerModule module;


    public PlayerJoinListener(ServerCore instance, JavaPlugin plugin) {
        final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
        this.plugin = plugin;
        this.storageModule = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.module = moduleManager.getRegisteredModule(PlayerModule.class);
        this.dataStorage = instance.getDataStorage();
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        //dataStorage.load(player);
    }
}
