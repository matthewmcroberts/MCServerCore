package com.matthew.template.bukkit.commands;

import com.matthew.template.bukkit.modules.messages.MessageModule;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.player.data.PlayerData;
import com.matthew.template.common.modules.ranks.RankModule;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public abstract class BaseCommand implements TabExecutor {

    protected final String permission;

    protected final PlayerModule playerModule;

    protected final RankModule rankModule;

    protected final MessageModule messageModule;

    protected final Map<String, BiConsumer<UUID, String[]>> commandActions = new HashMap<>();


    public BaseCommand(String permission) {
        this.permission = permission;
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
            Bukkit.getLogger().warning("Unexpected outcome occurred while attempting to execute command: '"
                    + player.getName() + "' (sender) not found in PlayerData cache.");
            return true;
        }

        if (!player.hasPermission(permission)) {
            messageModule.sendMessage(player, "noperm");
            return true;
        }

        if (args.length == 0) {
            messageModule.sendMessage(player, "usage");
            return true;
        }

        return execute(player, args);
    }


    /**
     * Executes the specific command logic.
     *
     * @param player the player who executed the command
     * @param args the arguments passed with the command
     * @return true if the command was executed successfully, false otherwise
     */
    protected abstract boolean execute(Player player, String[] args);

    /**
     * Registers the actions associated with the command.
     * This method is intended to be implemented by subclasses to define
     * the actions that can be performed by the command.
     */
    protected abstract void registerActions();
}
