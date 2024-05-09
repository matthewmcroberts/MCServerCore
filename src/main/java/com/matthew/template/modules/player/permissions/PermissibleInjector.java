package com.matthew.template.modules.player.permissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;

import java.lang.reflect.Field;

public class PermissibleInjector {

    private Field permissibleHumanEntityField;

    public PermissibleInjector() {
        //TODO: Better alternatives for testing rather than just calling Bukkit.getVersion();
        final String version = Bukkit.getVersion();

        try {
           permissibleHumanEntityField = Class
                   .forName("org.bukkit.craftbukkit." + version + ".entity.CraftHumanEntity")
                   .getDeclaredField("perm");
           permissibleHumanEntityField.setAccessible(true);

            final Field attachmentsField = PermissibleBase.class.getDeclaredField("attachments");
            attachmentsField.setAccessible(true);

            final Field permissibleBaseAttachmentsField = PermissibleBase.class.getDeclaredField("attachments");
            permissibleBaseAttachmentsField.setAccessible(true);

        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    public void injectPlayer(Player player) throws IllegalAccessException {
        PlayerPermissible permissible = new PlayerPermissible(player);
        permissibleHumanEntityField.set(player, permissible);
    }
}
