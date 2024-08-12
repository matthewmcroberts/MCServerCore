package com.matthew.template.bukkit.commands.rank;

import com.matthew.template.bukkit.annotations.RegisterCommand;
import com.matthew.template.bukkit.commands.BaseCommand;
import com.matthew.template.common.modules.player.data.PlayerData;
import com.matthew.template.common.modules.ranks.data.RankData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@RegisterCommand(name = "rank")
public class RankCommand extends BaseCommand {


    public RankCommand() {
        super("rank.use");
    }

    @Override
    protected boolean execute(Player player, String[] args) {
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
            List<String> rankNames = rankModule.getRanks().stream()
                    .map(RankData::getName)
                    .collect(Collectors.toList());
            if ("get".equalsIgnoreCase(args[0]) || "info".equalsIgnoreCase(args[0])) {
                return rankNames.stream()
                        .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                return rankNames.stream()
                        .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        return completions;
    }

    @Override
    protected void registerActions() {
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
