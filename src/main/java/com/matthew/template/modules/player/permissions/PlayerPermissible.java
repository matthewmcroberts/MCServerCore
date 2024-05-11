package com.matthew.template.modules.player.permissions;

import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.player.structure.PlayerData;
import com.matthew.template.modules.ranks.RankModule;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class PlayerPermissible extends PermissibleBase {
    private JavaPlugin plugin;

    private RankModule rankModule;

    private DataStorageModule storageModule;

    private PlayerData playerData;

    public PlayerPermissible(Player player, JavaPlugin plugin) {
        super(player);
        this.plugin = plugin;
        this.rankModule = ServerModuleManager.getInstance().getRegisteredModule(RankModule.class);
        this.storageModule = ServerModuleManager.getInstance().getRegisteredModule(DataStorageModule.class);
        this.playerData = Objects.requireNonNull(storageModule).getPlayerData(player);
        if (playerData == null) { //Only way playerData would be null is if the server was reloaded (Which shouldn't be done)
            PlayerData newPlayerData = storageModule.createPlayerData(player);
            this.playerData = storageModule.getPlayerData(newPlayerData.getName());
        }
    }

    @Override
    public boolean isOp() {
        return super.isOp();
    }

    @Override
    public void setOp(boolean value) {
        super.setOp(value);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return isPermissionSet(perm.getName());
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return super.isPermissionSet(name);
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return hasPermission(perm.getName());
    }

    @Override
    public boolean hasPermission(@NotNull String inName) {
        Rank rank = playerData.getRank();

        for(String perm: rank.getPermissions()) {
            if(perm.equalsIgnoreCase(inName)) {
                return true;
            }
        }
        return super.hasPermission(inName);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return super.addAttachment(plugin, name, value);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return super.addAttachment(plugin);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {
            super.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        super.recalculatePermissions();
    }

    public synchronized void clearPermissions() {
        super.clearPermissions();
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return super.getEffectivePermissions();
    }
}
