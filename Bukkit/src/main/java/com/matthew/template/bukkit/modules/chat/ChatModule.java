package com.matthew.template.bukkit.modules.chat;

import com.matthew.template.bukkit.events.listeners.ChatListener;
import com.matthew.template.bukkit.modules.chat.api.ChatChannel;
import com.matthew.template.bukkit.modules.chat.api.ChatRenderer;
import com.matthew.template.bukkit.modules.chat.channels.BuiltInChatChannel;
import com.matthew.template.bukkit.modules.messages.MessageModule;
import com.matthew.template.bukkit.utils.ChatColorUtils;
import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.PlayerModule;
import com.matthew.template.common.modules.player.data.PlayerData;
import com.matthew.template.common.modules.ranks.data.RankData;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RequiredArgsConstructor
public class ChatModule implements ServerModule {

    private final JavaPlugin plugin;

    private Listener listener;

    private MessageModule messages;

    private PlayerModule playerModule;

    // Maps each chat channel to a function that determines the audience for a given player.
    private final Map<ChatChannel, Function<Player, Audience>> audienceFunctions = new HashMap<>();

    // Maps each chat channel to a renderer that formats chat messages.
    private final Map<ChatChannel, ChatRenderer> renderers = new HashMap<>();

    // Tracks the chat channel each player is currently in.
    private final Map<Player, ChatChannel> playerChannels = new HashMap<>();

    // Keeps track of silenced chat channels where messages are temporarily not allowed.
    private final Set<ChatChannel> silencedChannels = new HashSet<>();

    /**
     * Sets the function that determines the audience for a given chat channel.
     *
     * @param channel the chat channel
     * @param audienceFunction the function that returns the audience for a player
     */
    public void setAudienceFunction(ChatChannel channel, Function<Player, Audience> audienceFunction) {
        audienceFunctions.put(channel, audienceFunction);
    }

    /**
     * Sets the renderer responsible for formatting messages in a chat channel.
     *
     * @param channel the chat channel
     * @param renderer the renderer that formats messages
     */
    public void setChatRenderer(ChatChannel channel, ChatRenderer renderer) {
        renderers.put(channel, renderer);
    }

    /**
     * Sets the chat channel that a player is currently in.
     *
     * @param player the player
     * @param channel the chat channel
     */
    public void setChatChannel(Player player, ChatChannel channel) {
        playerChannels.put(player, channel);
    }

    /**
     * Sends a message to a chat channel's audience after rendering it.
     *
     * @param channel the chat channel
     * @param player the player sending the message
     * @param message the message to send
     */
    public void sendToChatChannel(ChatChannel channel, Player player, Component message) {
        if (isChatSilenced(channel)) {
            return;
        }
        ChatRenderer renderer = renderers.get(channel);
        Audience audience = audienceFunctions.get(channel).apply(player);
        Component renderedMessage = renderer.render(player, message);
        audience.sendMessage(renderedMessage);
    }

    /**
     * Checks if a chat channel is currently silenced.
     *
     * @param channel the chat channel
     * @return true if the channel is silenced, false otherwise
     */
    public boolean isChatSilenced(ChatChannel channel) {
        return silencedChannels.contains(channel);
    }

    /**
     * Sets the silenced state of a chat channel.
     *
     * @param channel the chat channel
     * @param silenced true to silence the channel, false to unsilence it
     */
    public void setChatSilence(ChatChannel channel, boolean silenced) {
        if (silenced) {
            silencedChannels.add(channel);
        } else {
            silencedChannels.remove(channel);
        }
    }

    /**
     * Asynchronously checks if a message contains inappropriate content.
     *
     * @param text the message text
     * @return a CompletableFuture that completes with true if the message is filtered, false otherwise
     */
    public CompletableFuture<Boolean> isFilteredAsync(String text) {
        return CompletableFuture.supplyAsync(() -> isFiltered(text));
    }

    /**
     * Synchronously checks if a message contains inappropriate content.
     *
     * @param text the message text
     * @return true if the message is filtered, false otherwise
     */
    private boolean isFiltered(String text) {
        return text.toLowerCase().contains("badword");
    }

    /**
     * Handles player chat events by processing messages and sending them to the appropriate chat channel.
     *
     * @param e the AsyncChatEvent
     */
    public void handleAsyncChatEvent(AsyncChatEvent e) {
        Player player = e.getPlayer();
        Component message = e.message();

        ChatChannel channel = playerChannels.getOrDefault(player, BuiltInChatChannel.GLOBAL);

        if (isChatSilenced(channel)) {
            e.setCancelled(true);
            messages.sendMessage(player, "silenced");
            return;
        }

        e.setCancelled(true);

        // For filtering purposes, convert the Component to a string (using legacy formatting)
        String messageText = LegacyComponentSerializer.legacyAmpersand().serialize(message);

        isFilteredAsync(messageText).thenAccept(isFiltered -> {
            if (isFiltered) {
                messages.sendMessage(player, "inappropriate");
            } else {
                sendToChatChannel(channel, player, message);
            }
        });
    }

    /**
     * Sets up the chat module by initializing channels and registering events.
     */
    @Override
    public void setUp() {
        messages = ServerModuleManager.getInstance().getRegisteredModule(MessageModule.class);
        playerModule = ServerModuleManager.getInstance().getRegisteredModule(PlayerModule.class);

        setAudienceFunction(BuiltInChatChannel.GLOBAL, player -> Audience.audience(Bukkit.getOnlinePlayers()));

        setChatRenderer(BuiltInChatChannel.GLOBAL, (player, message) -> {
            PlayerData playerData = playerModule.getPlayerData(player);
            RankData rank = playerData.getRankData();

            TextColor prefixColor = ChatColorUtils.getColorFromName(rank.getPrefixColor());
            TextColor chatColor = ChatColorUtils.getColorFromName(rank.getChatColor());

            return Component.text(rank.getPrefix(), prefixColor)
                    .append(Component.text(" " + player.getName() + ": ", prefixColor))
                    .append(message.color(chatColor));
        });

        this.listener = new ChatListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * Tears down the chat module by clearing all stored data.
     */
    @Override
    public void teardown() {
        audienceFunctions.clear();
        renderers.clear();
        playerChannels.clear();
        silencedChannels.clear();
    }
}
