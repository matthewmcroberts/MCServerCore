package com.matthew.template.bukkit.commands;

import com.matthew.template.bukkit.annotations.RegisterCommand;
import com.matthew.template.bukkit.modules.messages.MessageModule;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.player.data.PlayerData;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.ranks.data.RankData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@RegisterCommand(name = "rank")
public class RankCommand implements TabExecutor {

    private final PlayerModule playerModule;
    private final RankModule rankModule;
    private final MessageModule messageModule;
    private final Map<String, BiConsumer<UUID, String[]>> commandActions = new HashMap<>();

    public RankCommand() {
        this.playerModule = ServerModuleManager.getInstance().getRegisteredModule(PlayerModule.class);
        this.rankModule = ServerModuleManager.getInstance().getRegisteredModule(RankModule.class);
        this.messageModule = ServerModuleManager.getInstance().getRegisteredModule(MessageModule.class);
        registerActions();
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

        if (!player.hasPermission("rankcommand.use")) {
            messageModule.sendMessage(player, "noperm");
            return true;
        }

        if (args.length == 0) {
            messageModule.sendMessage(player, "usage");
            return true;
        }

        handleCommand(player, args);
        return true;
    }

    /*
    TODO: Make it so that subCommands appear before playerNames. Not entirely sure how easy that is or if it is even possible
     considering the internal lexicographic sorting mechanism in place by minecraft
     */
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> subcommands = Arrays.asList("get", "info", "list");
            List<String> playerNames = Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());

            completions.addAll(subcommands);
            completions.addAll(playerNames);
            return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            if ("get".equalsIgnoreCase(args[0]) || "info".equalsIgnoreCase(args[0])) {
                List<String> rankNames = rankModule.getRanks().stream()
                        .map(RankData::getName)
                        .collect(Collectors.toList());
                return rankNames.stream()
                        .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                List<String> rankNames = rankModule.getRanks().stream()
                        .map(RankData::getName)
                        .collect(Collectors.toList());
                return rankNames.stream()
                        .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        return completions;
    }


    private void handleCommand(Player player, String[] args) {
        String actionArg = args[0].toLowerCase();

        if (args.length == 2) {
            Player target = Bukkit.getPlayer(actionArg);
            BiConsumer<UUID, String[]> action = commandActions.get(target != null ? "set" : actionArg);

            if (action != null) {
                action.accept(player.getUniqueId(), args);
            } else {
                messageModule.sendMessage(player, "playernotfound", args[0]);
            }
        } else if (args.length == 1 && "list".equals(actionArg)) {
            player.sendMessage(rankModule.getRanks().toString());
        } else {
            messageModule.sendMessage(player, "usage");
        }
    }

    private void registerActions() {
        commandActions.put("set", (uuid, args) -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                Bukkit.getLogger().warning("Player (sender) with UUID " + uuid + " was not online when running 'set' for rank command.");
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            final String PLAYER_NOT_FOUND = messageModule.buildMessage("playernotfound", args[0]);
            if (target == null) {
                player.sendMessage(PLAYER_NOT_FOUND);
                return;
            }

            RankData rank = rankModule.getRank(args[1]);
            if (rank == null) {
                messageModule.sendMessage(player, "ranknotfound", args[1]);
                return;
            }

            PlayerData targetData = playerModule.getPlayerData(target);
            if (targetData == null) {
                player.sendMessage(PLAYER_NOT_FOUND);
                Bukkit.getLogger().warning("Online player: '" + target.getName() + "' not found in cache");
                return;
            }

            targetData.setRankData(rank);
            targetData.setModified(true);

            messageModule.sendMessage(target, "targetranksuccess", rank.getName());
            messageModule.sendMessage(player, "senderranksuccess", player.getName(), rank.getName());
        });

        commandActions.put("get", (uuid, args) -> {
            Optional<Player> optionalPlayer = Optional.ofNullable(Bukkit.getPlayer(uuid));
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                player.sendMessage("get");
            } else {
                Bukkit.getLogger().warning("Player with UUID " + uuid + " was not online when running 'get' for rank command.");
            }
        });

        commandActions.put("info", (uuid, args) -> {
            Optional<Player> optionalPlayer = Optional.ofNullable(Bukkit.getPlayer(uuid));
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                player.sendMessage("info");
            } else {
                Bukkit.getLogger().warning("Player with UUID " + uuid + " was not online when running 'info' for rank command.");
            }
        });
    }
}
