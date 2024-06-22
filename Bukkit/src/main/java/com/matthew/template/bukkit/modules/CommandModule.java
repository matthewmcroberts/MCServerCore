package com.matthew.template.bukkit.modules;

import com.matthew.template.bukkit.commands.commands.RankCommand;
import com.matthew.template.bukkit.utils.CommandUtil;
import com.matthew.template.common.apis.ServerModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CommandModule implements ServerModule {

    private final JavaPlugin plugin;

    private List<CommandExecutor> commands;

    public CommandModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setUp() {
        commands = new ArrayList<>();

        buildCommands();

        for(CommandExecutor command: commands) {
            CommandUtil.register(command);
        }
    }

    @Override
    public void teardown() {

    }

    private void buildCommands() {
        commands.add(new RankCommand());
    }
}
