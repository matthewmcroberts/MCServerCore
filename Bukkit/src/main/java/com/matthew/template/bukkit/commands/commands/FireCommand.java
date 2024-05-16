package com.matthew.template.bukkit.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FireCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public FireCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = sender instanceof Player ? (Player) sender : null;

        if(player == null) {
            sender.sendMessage("You must be a player to use this command");
            return true;
        }

        if(!(player.hasPermission("fire.use"))) {
            player.sendMessage(ChatColor.RED + "Insufficient permissions");
            return true;
        }

        if(args.length != 1) {
            player.sendMessage(ChatColor.RED + "Invalid usage. /fire <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage("Player not found");
            return true;
        }

        target.sendMessage(ChatColor.GOLD + "You feel a burn coming on in 3 seconds...");

        new BukkitRunnable() {
            @Override
            public void run() {
                if(target.isDead()) {
                    target.setFireTicks(0);
                    cancel();
                    return;
                }
                    target.setFireTicks(20 * 3);
            }
        }.runTaskTimer(plugin, 20*3, 0);

        player.sendMessage("out");


        return true;
    }
}
