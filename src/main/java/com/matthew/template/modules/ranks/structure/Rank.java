package com.matthew.template.modules.ranks.structure;

import com.matthew.template.modules.manager.ServerModuleManager;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class Rank {

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
    private final String name;
    private final Set<String> permissions;
    private final String color;
    private final String chatColor;
    private final String prefix;
    private final boolean isDefault;
    private final boolean isStaffRank;

    public Rank(String name, String color, String chatColor, String prefix, boolean isDefault, boolean isStaffRank, Set<String> permissions) {
        this.name = name;
        this.color = color;
        this.chatColor = chatColor;
        this.prefix = prefix;
        this.isDefault = isDefault;
        this.permissions = permissions;
        this.isStaffRank = isStaffRank;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isStaffRank() {
        return isStaffRank;
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

    public Set<String> getPermissions() {
        return permissions;
    }

    public String getChatColor() {
        return chatColor;
    }
}
