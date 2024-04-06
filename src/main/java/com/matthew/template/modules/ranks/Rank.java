package com.matthew.template.modules.ranks;

import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.permissions.PermissionsModule;
import org.bukkit.ChatColor;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;

public class Rank {

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
    private final String name;
    private final PermissionsModule permissions = moduleManager.getRegisteredModule(PermissionsModule.class);
    private final ChatColor color;
    private final String prefix;
    private final boolean isDefault;

    public Rank(String name, ChatColor color, String prefix, boolean isDefault) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PermissionAttachment> getPermissions() {
        return permissions;
    }

    public void addPermission(PermissionAttachment perm) {
        if(permissions == null) {
            return;
        }
        permissions.add(perm);
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
