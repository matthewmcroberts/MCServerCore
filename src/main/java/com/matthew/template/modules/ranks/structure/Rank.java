package com.matthew.template.modules.ranks.structure;

import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.permissions.PermissionsModule;
import org.bukkit.ChatColor;

public class Rank {

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
    private final RankType rankType;
    private final String name;
    private final PermissionsModule permissions = moduleManager.getRegisteredModule(PermissionsModule.class);
    private final ChatColor color;
    private final String prefix;
    private final boolean isDefault;

    public Rank(RankType rankType, String prefix, boolean isDefault) {
        this.rankType = rankType;
        this.name = rankType.getName();
        this.color = rankType.getColor();
        this.prefix = prefix;
        this.isDefault = isDefault;
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
}
