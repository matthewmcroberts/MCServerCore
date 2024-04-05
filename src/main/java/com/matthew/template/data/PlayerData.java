package com.matthew.template.data;

import com.matthew.template.modules.storage.cache.Cache;
import org.bukkit.ChatColor;

import java.util.UUID;

public final class PlayerData {

    private final Cache cache = Cache.getInstance();
    private final String playerName;
    private final UUID uuid;
    private ChatColor color;

    public PlayerData(String playerName, UUID uuid) {
        this.playerName = playerName;
        this.uuid = uuid;
    }

}
