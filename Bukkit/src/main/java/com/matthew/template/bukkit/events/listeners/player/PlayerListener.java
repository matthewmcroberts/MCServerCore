package com.matthew.template.bukkit.events.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class PlayerListener implements Listener {

    private static final PlayerListener instance = new PlayerListener();

    private PlayerListener() {
    }

    public static PlayerListener getInstance() {
        return instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatEarly(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatLate(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(false);
    }
}
