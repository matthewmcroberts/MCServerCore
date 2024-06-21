package com.matthew.template.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

/**
 * Utility methods to handle Command.
 */
public final class CommandUtil {
    private static final String COMMAND_NAMESPACE = "core";

    private CommandUtil() {}

    /**
     * Use Reflection to get the commandMap field.
     *
     * @return the CommandMap
     */
    private static CommandMap getCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to get CommandMap", e);
        }
    }

    /**
     * Register a Command to the CommandMap.
     *
     * @param command the command
     */
    public static void register(final Command command) {
        getCommandMap().register(COMMAND_NAMESPACE, command);
    }
}
