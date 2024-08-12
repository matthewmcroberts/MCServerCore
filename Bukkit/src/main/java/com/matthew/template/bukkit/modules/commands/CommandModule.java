package com.matthew.template.bukkit.modules.commands;

import com.matthew.template.bukkit.modules.commands.data.CommandData;
import com.matthew.template.bukkit.modules.commands.factory.CommandFactory;
import com.matthew.template.bukkit.utils.CommandUtil;
import com.matthew.template.common.apis.ServerModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandModule implements ServerModule {

    private final JavaPlugin plugin;

    private final String BASE_PACKAGE = "com.matthew.template.bukkit.commands";

    private final CommandFactory commandFactory;

    public CommandModule(JavaPlugin plugin) {
        this.plugin = plugin;
        this.commandFactory = new CommandFactory(BASE_PACKAGE);
    }

    @Override
    public void setUp() {
        List<CommandData> commandDataList = commandFactory.createCommands();

        Bukkit.getLogger().info(commandDataList.toString());

        for (CommandData commandData : commandDataList) {
            Bukkit.getLogger().info(commandData.toString());
            CommandUtil.register(commandData.getCommandName(), commandData.getCommandExecutor());
        }
    }

    @Override
    public void teardown() {
        // necessary teardown logic here
    }
}

