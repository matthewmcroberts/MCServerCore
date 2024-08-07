package com.matthew.template.bukkit.commands;

import com.matthew.template.bukkit.annotations.RegisterCommand;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.player.data.PlayerData;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.ranks.data.RankData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RegisterCommand(name = "rank")
public class RankCommand implements TabExecutor {

    private PlayerModule playerModule = ServerModuleManager.getInstance().getRegisteredModule(PlayerModule.class);

    private RankModule rankModule = ServerModuleManager.getInstance().getRegisteredModule(RankModule.class);

    private final Map<String, Consumer<PlayerData>> singleArgCommandActions = new HashMap<>();

    private final Map<String, BiConsumer<PlayerData, RankData>> doubleArgCommandActions = new HashMap<>();


    public RankCommand() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            Bukkit.getLogger().info("Sender must be a player");
            return true;
        }

        PlayerData playerData = playerModule.getPlayerData(player);

        if (playerData == null) {
            Bukkit.getLogger().warning("Unexpected outcome occurred while attempting to execute RankCommand: '"
                    + player.getName() + "' (sender) not found in PlayerData cache.");
            return true;
        }

        if(!player.hasPermission("rankcommand.use")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if(args.length != 2) {
            player.sendMessage(ChatColor.RED + "Incorrect usage");
            return true;
        }

        String actionArg = args[0].toLowerCase();


        if(!"get".equals(actionArg)) {

            Player target = Bukkit.getPlayer(args[1]);

            if(target == null) {
                player.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found");
                return true;
            }

            Consumer<PlayerData> action = singleArgCommandActions.get(actionArg);

            if(action == null) {
                Bukkit.getLogger().severe("Missing action for RankCommand'" + actionArg + "'");
                return true;
            }

            action.accept(playerData);

        } else {

        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }

    public void registerActions() {
        doubleArgCommandActions.put("set", (player, rank) -> {

        });

        singleArgCommandActions.put("get", (player) -> {

        });
    }
}
