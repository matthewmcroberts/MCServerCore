package com.matthew.template.bukkit.utils;

import com.matthew.template.bukkit.ServerCore;
import com.matthew.template.bukkit.commands.commands.RankCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Utility methods to handle Command.
 */
public final class CommandUtil {

    private static final JavaPlugin plugin = ServerCore.getInstance();

    private CommandUtil() {}

    /**
     * Register a Command to the CommandMap.
     *
     * @param command the command
     */
    public static void register(final CommandExecutor command) {
        Objects.requireNonNull(plugin.getCommand("rank")).setExecutor(command);
    }
}
