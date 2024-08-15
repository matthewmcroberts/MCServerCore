package com.matthew.template.bukkit.events.listeners;

import com.matthew.template.bukkit.modules.chat.ChatModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final ChatModule module;

    public ChatListener(final ChatModule chatModule) {
        this.module = chatModule;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        // Delegate the event handling to the ChatModule for now
        module.handleAsyncPlayerChatEvent(e);
    }
}
