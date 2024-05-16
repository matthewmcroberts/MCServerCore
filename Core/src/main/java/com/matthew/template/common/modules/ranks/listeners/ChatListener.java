package com.matthew.template.common.modules.ranks.listeners;

import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatListener implements Listener {

    private final JavaPlugin plugin;
    private final ServerModuleManager moduleManager;
    private final RankModule module;

    public ChatListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.moduleManager = ServerModuleManager.getInstance();
        this.module = this.moduleManager.getRegisteredModule(RankModule.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
    }
}
