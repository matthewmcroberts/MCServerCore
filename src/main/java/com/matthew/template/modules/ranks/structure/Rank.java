package com.matthew.template.modules.ranks.structure;

import com.matthew.template.modules.manager.ServerModuleManager;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class Rank {

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
    private final RankType rankType;
    private final String name;
    private final Set<String> permissions;
    private final ChatColor color;
    private final String prefix;
    private final boolean isDefault;

    public Rank(RankType rankType, String prefix, boolean isDefault) {
        this.rankType = rankType;
        this.name = rankType.getName();
        this.color = rankType.getColor();
        this.prefix = prefix;
        this.isDefault = isDefault;
        this.permissions = new HashSet<>();
    }

    public RankType getType() {
        return this.rankType;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean hasPermission(String node) {
        return permissions.contains(node);
    }

    public boolean addPermission(String node) {
        if (permissions.contains(node)) {
            return false;
        }
        permissions.add(node);
        return true;
    }
}
