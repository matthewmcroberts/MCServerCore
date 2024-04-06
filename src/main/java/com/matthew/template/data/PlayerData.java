package com.matthew.template.data;

import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class PlayerData {

    private final String playerName;
    private final UUID uuid;

    private Rank rank;
    private ChatColor color;

    public PlayerData(Player player, Rank rank) {
        this.playerName = player.getDisplayName();
        this.uuid = player.getUniqueId();
        this.rank = rank;

    }

}
