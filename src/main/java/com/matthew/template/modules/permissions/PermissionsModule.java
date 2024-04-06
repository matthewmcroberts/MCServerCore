package com.matthew.template.modules.permissions;

import com.matthew.template.api.ServerModule;
import com.matthew.template.modules.permissions.commands.PermissionsCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class PermissionsModule implements ServerModule {

    /*
    TODO: Load permissions of a rank from a rank.yml file and store the playerPermissions in the cache instead of this file
     */

    private final JavaPlugin plugin;

    //To be removed
    private final Map<UUID, List<PermissionAttachment>> playerPermissions;

    public PermissionsModule(JavaPlugin plugin) {
        this.plugin = plugin;
        playerPermissions = new HashMap<>();
    }

    @Override
    public void setUp() {
        CommandExecutor command = new PermissionsCommand(this);
        Objects.requireNonNull(plugin.getCommand("perm")).setExecutor(command);
    }

    @Override
    public void teardown() {
        //no allocated resource in need of a tear down
    }

    public boolean addPermission(Player player, String permission) {
        List<PermissionAttachment> permissions = playerPermissions.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>());

        if (permissions.isEmpty()) {
            PermissionAttachment permissionAttachment = player.addAttachment(plugin);
            permissionAttachment.setPermission(permission, true);
            permissions.add(permissionAttachment);
            return true;
        }

        for (PermissionAttachment perm : permissions) {
            if (perm.toString().equals(permission)) {
                return false;
            }
        }

        PermissionAttachment permissionAttachment = player.addAttachment(plugin);
        permissionAttachment.setPermission(permission, true);
        permissions.add(permissionAttachment);
        return true;
    }

    public boolean removePermission(Player player, String permission) {
        if (exists(player)) {
            List<PermissionAttachment> permissions = playerPermissions.get(player.getUniqueId());

            for (Iterator<PermissionAttachment> it = permissions.iterator(); it.hasNext(); ) {
                PermissionAttachment perm = it.next();
                if (perm.getPermissions().get(permission) != null) {
                    it.remove();
                    player.removeAttachment(perm);
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getAllPermissions(Player player) {
        List<String> permissionsList = new ArrayList<>();
        Set<String> keys;
        if (exists(player)) {
            for (PermissionAttachment permissions : playerPermissions.get(player.getUniqueId())) {
                keys = permissions.getPermissions().keySet();
                permissionsList.addAll(keys);
            }
            return permissionsList;
        }
        return new ArrayList<>();
    }

    private boolean exists(Player player) {
        return playerPermissions.get(player.getUniqueId()) != null;
    }

}
