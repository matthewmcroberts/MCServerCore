package com.matthew.template.data;

import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
  The purpose of PlayerData is to keep track of data that will end up being stored or accessed in the database
 */
public final class PlayerData {

    private final String playerName;
    private final UUID uuid;
    private final Player player;
    private Rank rank;
    private ChatColor chatColor;
    private boolean isStaff;

    private long playTime;

    public PlayerData(final Player player, final Rank rank, final long playTime) {
        this.playerName = player.getDisplayName();
        this.uuid = player.getUniqueId();
        this.rank = rank;
        this.isStaff = rank.isStaffRank();
        this.chatColor = rank.getColor();
        this.player = player;
        this.playTime = playTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public Player toBukkitPlayer() {
        return player;
    }

    public Rank getRank() {
        return rank;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public void setChatColor(final ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(final long playTime) {
        this.playTime = playTime;
    }
}
