package com.matthew.template.bukkit.utils;

import com.matthew.template.bukkit.ServerCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Utility methods to handle Command.
 */
public final class CommandUtil {

    private static final JavaPlugin plugin = ServerCore.getInstance();

    private CommandUtil() {}

    public static void register(String commandName, CommandExecutor executor) {
        Bukkit.getLogger().info(commandName + " " + executor);
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(executor);
    }
}
