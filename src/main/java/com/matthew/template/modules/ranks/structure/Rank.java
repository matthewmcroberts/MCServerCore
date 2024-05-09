package com.matthew.template.modules.ranks.structure;

import com.matthew.template.modules.manager.ServerModuleManager;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.Collections;
import java.util.List;

public class Rank {

    private final ServerModuleManager moduleManager;
    private String name;
    private List<String> permissions;
    private String color;
    private String chatColor;
    private String prefix;
    private boolean isDefault;
    private boolean isStaff;

    //called during snakeyaml reflection in RankConfigManager if rank.yml doesn't exist
    public Rank() {
        moduleManager = ServerModuleManager.getInstance();
    }

    //called during snakeyaml reflection in RankConfigManager if rank.yml is empty
    public Rank(String name) {
        this.name = name;
        moduleManager = ServerModuleManager.getInstance();
    }

    public Rank(String name, String color, String chatColor, String prefix, boolean isDefault, boolean isStaff, List<String> permissions) {
        this.name = name;
        this.color = color;
        this.chatColor = chatColor;
        this.prefix = prefix;
        this.isDefault = isDefault;
        this.permissions = permissions;
        this.isStaff = isStaff;
        moduleManager = ServerModuleManager.getInstance();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setIsDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setIsStaff(final boolean isStaff) {
        this.isStaff = isStaff;
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

    public List<String> getPermissions() {
        return permissions != null ? permissions : Collections.emptyList();
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getChatColor() {
        return chatColor;
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    public boolean hasAllProperties() {
        return this.name != null || this.color != null || this.chatColor != null || this.prefix != null || this.permissions != null;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "name='" + name + '\'' +
                ", permissions=" + permissions +
                ", color='" + color + '\'' +
                ", chatColor='" + chatColor + '\'' +
                ", prefix='" + prefix + '\'' +
                ", isDefault=" + isDefault +
                ", isStaff=" + isStaff +
                '}';
    }
}
