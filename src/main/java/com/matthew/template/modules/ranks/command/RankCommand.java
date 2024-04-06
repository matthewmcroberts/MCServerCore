package com.matthew.template.modules.ranks.command;

import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.ranks.RankModule;
import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
    RankModule rankModule = moduleManager.getRegisteredModule(RankModule.class);

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

        for(Rank rank: rankModule.getRanks()) {
            player.sendMessage(rank.getName());
        }

        return true;
    }
}
