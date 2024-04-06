package com.matthew.template.modules.ranks;

import org.bukkit.ChatColor;

public enum RankType {
    ADMIN("ADMIN", ChatColor.RED),
    MEMBER("MEMBER", ChatColor.GRAY);

    private final String name;
    private final ChatColor color;

    RankType(String name, ChatColor color) {
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}
