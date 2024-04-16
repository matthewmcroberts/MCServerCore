package com.matthew.template.modules.ranks.command;

import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.ranks.RankModule;
import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RankCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final ServerModuleManager moduleManager;
    private final RankModule module;

    public RankCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(RankModule.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = sender instanceof Player ? (Player) sender : null;

        if(player == null) {
            Bukkit.getLogger().info("You must be a player to use this command");
            return true;
        }

        if(args.length != 0) {
            player.sendMessage("invalid usage");
            return true;
        }

        for(Rank rank: module.getRanks()) {
            player.sendMessage(rank.getName() + ": " + rank.getPermissions().toString());
        }

        return true;
    }
}
