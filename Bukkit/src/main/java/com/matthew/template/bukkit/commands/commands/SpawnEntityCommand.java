package com.matthew.template.bukkit.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntityCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (sender instanceof Player) ? (Player) sender : null;

        if(player == null) {
            Bukkit.getLogger().severe("You must be a player to use this command");
            return true;
        }

        if(!player.hasPermission("spawnentity.use")) {
            player.sendMessage(ChatColor.RED + "Insufficient permissions");
            return true;
        }

        EntityType entityType;

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Incorrect usage: \n" +
                    "- /spawnentity <type> <player>\n" +
                    "- /spawnentity <type> <x> <y> <z>");
            return true;
        }

        try {
            entityType = EntityType.valueOf(args[0].toUpperCase());
        } catch (Throwable t) {
            player.sendMessage(ChatColor.RED + "No entity of type " + args[0] + " found.");
            return true;
        }

        if (!entityType.isAlive() || !entityType.isSpawnable()) {
            player.sendMessage(ChatColor.RED + "Entity type " + entityType.name() + " was unable to be spawned");
        }

        if (args.length == 4) {
            Integer x = parseNumber(args, 1) ? Integer.parseInt(args[1]) : null;
            Integer y = parseNumber(args, 2) ? Integer.parseInt(args[2]) : null;
            Integer z = parseNumber(args, 3) ? Integer.parseInt(args[3]) : null;

            if (x == null || y == null || z == null) {
                player.sendMessage(ChatColor.RED + "Please make sure your x, y, and z are real whole numbers");
                return true;
            }

            player.getWorld().spawnEntity(new Location(player.getWorld(), x, y, z), entityType);
        } else if (args.length == 2) {
            Player targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
            targetPlayer.getWorld().spawnEntity(targetPlayer.getLocation(), entityType);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        Player player = (Player) sender;
        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1:
                for (EntityType entityType : EntityType.values()) {
                    if (entityType.isSpawnable() && entityType.isAlive()) {
                        String name = entityType.name().toLowerCase();

                        if (name.startsWith(args[0])) {
                            completions.add(name);
                        }
                    }
                }
                break;
            case 2:
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    String name = onlinePlayer.getName();
                    if(name.startsWith(args[1])) {
                        completions.add(name);
                    }
                }
                completions.add(String.valueOf(player.getLocation().getBlockX()));
                break;
            case 3:
                completions.add(String.valueOf(player.getLocation().getBlockY()));
                break;
            case 4:
                completions.add(String.valueOf(player.getLocation().getBlockZ()));
                break;
        }
        return completions;
    }

    private boolean parseNumber(String[] args, int index) {
        try {
            Integer.parseInt(args[index]);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
