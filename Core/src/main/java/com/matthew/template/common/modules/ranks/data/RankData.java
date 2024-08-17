package com.matthew.template.common.modules.ranks.data;

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
public class RankData {

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();

    @NonNull
    private String name;
    private List<String> permissions;
    private String chatColor;
    private String prefixColor;
    private String prefix;
    private boolean isDefault;
    private boolean isStaff;

    // Called during snakeyaml reflection in RankConfigManager if rank.yml is empty
    public RankData(String name) {
        this.name = name;
    }

    public RankData(String name, String chatColor, String prefixColor, String prefix, boolean isDefault, boolean isStaff, List<String> permissions) {
        this.name = name;
        this.chatColor = chatColor;
        this.prefixColor = prefixColor;
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
        return this.name != null || this.chatColor != null || this.prefix != null || this.permissions != null;
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
