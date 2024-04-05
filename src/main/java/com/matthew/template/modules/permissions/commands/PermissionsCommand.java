package com.matthew.template.modules.permissions.commands;

import com.matthew.template.util.messages.CommandErrorMessage;
import com.matthew.template.modules.permissions.PermissionsModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 /perm <add/remove/check> <node/player>
 */
public class PermissionsCommand implements CommandExecutor {

    private final String INVALID_USAGE = new CommandErrorMessage("perm",
            "add/remove/check", "node").build();

    private final PermissionsModule module;

    public PermissionsCommand(PermissionsModule module) {
        this.module = module;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            Bukkit.getLogger().info("You must be a player to use this command");
            return true;
        }

        if (!(player.hasPermission("permissions.use"))) {
            player.sendMessage(ChatColor.RED + "Insufficient permissions");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(INVALID_USAGE);
            return true;
        }

        String param1 = args[0].toLowerCase();

        switch (param1) {
            case "add":
                if (module.addPermission(player, args[1].toLowerCase())) {
                    player.sendMessage(ChatColor.GREEN + "Permission added");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "Permission not added" + param1);
                break;
            case "remove":
                if (module.removePermission(player, args[1].toLowerCase())) {
                    player.sendMessage(ChatColor.GREEN + "Permission removed");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "Permission not found");
                break;
            case "check":
                if (!(module.getAllPermissions(player).isEmpty())) {
                    player.sendMessage(module.getAllPermissions(player).toString());
                    return true;
                }
                player.sendMessage(ChatColor.RED + "No custom permissions set");
                break;
            default:
                player.sendMessage(INVALID_USAGE);
        }
        return true;
    }
}
