package com.matthew.template.bukkit.permissions;

import com.matthew.template.common.util.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class PermissibleInjector {

    private PermissibleInjector() {}

    public static void injectPlayer(JavaPlugin plugin, Player player) {
        try {
            Field playerPermissibleField = Class.forName("org.bukkit.craftbukkit." + ReflectionUtils.VERSION + ".entity.CraftHumanEntity").getDeclaredField("perm");
            playerPermissibleField.setAccessible(true);
            playerPermissibleField.set(player, new PlayerPermissible(player, plugin));
            playerPermissibleField.setAccessible(false);
        } catch (Exception e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }
}
