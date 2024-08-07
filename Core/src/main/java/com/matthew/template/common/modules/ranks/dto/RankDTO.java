package com.matthew.template.common.modules.ranks.dto;

import com.matthew.template.common.modules.manager.ServerModuleManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RankDTO {

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();

    @NonNull
    private String name;
    private List<String> permissions;
    private String color;
    private String chatColor;
    private String prefix;
    private boolean isDefault;
    private boolean isStaff;

    // Called during snakeyaml reflection in RankConfigManager if rank.yml is empty
    public RankDTO(String name) {
        this.name = name;
    }

    public RankDTO(String name, String color, String chatColor, String prefix, boolean isDefault, boolean isStaff, List<String> permissions) {
        this.name = name;
        this.color = color;
        this.chatColor = chatColor;
        this.prefix = prefix;
        this.isDefault = isDefault;
        this.isStaff = isStaff;
        this.permissions = permissions;
    }

    public boolean hasPermission(String node) {
        return permissions != null && permissions.contains(node);
    }

    public boolean addPermission(String node) {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        if (permissions.contains(node)) {
            return false;
        }
        permissions.add(node);
        return true;
    }

    public List<String> getPermissions() {
        return permissions != null ? permissions : Collections.emptyList();
    }

    public boolean hasAllProperties() {
        return this.name != null || this.color != null || this.chatColor != null || this.prefix != null || this.permissions != null;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
