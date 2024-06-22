package com.matthew.template.bukkit.modules.commands.data;

import org.bukkit.command.CommandExecutor;

/**
 * POJO for sending the command through the pipeline to be registered in CommandUtil
 */
public class CommandData {

    private final String commandName;

    private final CommandExecutor command;

    public CommandData(String commandName, CommandExecutor command) {
        this.commandName = commandName;
        this.command = command;
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandExecutor getCommandExecutor() {
        return command;
    }
}
