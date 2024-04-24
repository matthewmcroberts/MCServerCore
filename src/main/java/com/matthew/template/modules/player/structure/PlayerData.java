package com.matthew.template.modules.player.structure;

import com.google.gson.*;
import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.UUID;

/*
  The purpose of PlayerData is to keep track of data that will end up being stored or accessed in the database
 */
public final class PlayerData {

    private final String name;
    private final UUID uuid;
    private Rank rank;
    private String chatColor;
    private boolean isStaff;
    private long playTime;
    private boolean isModified;

    public PlayerData(final Player player, final Rank rank, final long playTime) {
        this.name = player.getDisplayName();
        this.uuid = player.getUniqueId();
        this.rank = rank;
        this.isStaff = rank.isStaff();
        this.chatColor = rank.getChatColor();
        this.playTime = playTime;
        this.isModified = false;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public Rank getRank() {
        return rank;
    }

    public String getChatColor() {
        return chatColor;
    }

    public void setChatColor(final String chatColor) {
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

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    @Override
    public String toString() {
        return "name: " + getName()
                + " uuid: " + getUniqueId().toString()
                + " rank: " + getRank().getName()
                + " chatColor: " + getChatColor()
                + " isStaff: " + isStaff()
                + " playTime: " + getPlayTime()
                + " isModified: " + isModified();
    }

    public static class PlayerSerializer implements JsonSerializer<PlayerData> {
        @Override
        public JsonElement serialize(PlayerData player, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.add("name", new JsonPrimitive(player.getName()));
            result.add("uuid", new JsonPrimitive(player.getUniqueId().toString()));
            result.add("rank", new JsonPrimitive(player.getRank().getName()));
            result.add("chatColor", new JsonPrimitive(player.getChatColor()));
            result.add("isStaff", new JsonPrimitive(player.isStaff()));
            result.add("playTime", new JsonPrimitive(player.getPlayTime()));
            return result;
        }
    }
}
