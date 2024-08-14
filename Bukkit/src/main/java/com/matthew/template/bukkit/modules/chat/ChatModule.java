package com.matthew.template.bukkit.modules.chat;

import com.matthew.template.bukkit.events.listeners.ChatListener;
import com.matthew.template.bukkit.modules.chat.api.Audience;
import com.matthew.template.bukkit.modules.chat.api.ChatChannel;
import com.matthew.template.bukkit.modules.chat.api.ChatRenderer;
import com.matthew.template.bukkit.modules.chat.channels.BuiltInChatChannel;
import com.matthew.template.bukkit.modules.chat.audience.PlayerAdapter;
import com.matthew.template.bukkit.modules.messages.MessageModule;
import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Inspiration on the idea of using ChatChannels and Audiences for implementing custom formatted messages and channels in
the future was taken from
https://studio.mineplex.com/docs/sdk/modules/chat

Thank you Mineplex!

Side Note: Though the idea was provided by Mineplex, the full implementation on the design, FOR THIS PROJECT ONLY, is
written by the Author: Matthew (GoofIt/Mahht)
 */

@RequiredArgsConstructor
public class ChatModule implements ServerModule {

    private final JavaPlugin plugin;

    private Listener listener;

    private MessageModule messages;

    private final Map<ChatChannel, Function<Player, Set<Audience>>> audienceFunctions = new HashMap<>();

    private final Map<ChatChannel, ChatRenderer> renderers = new HashMap<>();

    private final Map<Player, ChatChannel> playerChannels = new HashMap<>();

    private final Map<ChatChannel, Set<Audience>> channelAudiences = new HashMap<>();

    private final Set<ChatChannel> silencedChannels = new HashSet<>();

    public void setAudienceFunction(ChatChannel channel, Function<Player, Set<Audience>> audienceFunction) {
        audienceFunctions.put(channel, audienceFunction);
    }

    public void setChatRenderer(ChatChannel channel, ChatRenderer renderer) {
        renderers.put(channel, renderer);
    }

    public void setChatChannel(Player player, ChatChannel channel) {
        playerChannels.put(player, channel);
    }

    public void sendToChatChannel(ChatChannel channel, Player player, Component message) {
        if (isChatSilenced(channel)) {
            return;
        }
        ChatRenderer renderer = renderers.get(channel);
        Set<Audience> audience = audienceFunctions.get(channel).apply(player);
        Component renderedMessage = renderer.render(player, message);
        for (Audience a : audience) {
            a.sendMessage(renderedMessage);
        }
    }

    public boolean isChatSilenced(ChatChannel channel) {
        return silencedChannels.contains(channel);
    }

    public void setChatSilence(ChatChannel channel, boolean silenced) {
        if (silenced) {
            silencedChannels.add(channel);
        } else {
            silencedChannels.remove(channel);
        }
    }

    public CompletableFuture<Boolean> isFilteredAsync(String text) {
        return CompletableFuture.supplyAsync(() -> isFiltered(text));
    }

    //TODO: Implement censor
    private boolean isFiltered(String text) {
        return text.toLowerCase().contains("badword");
    }

    public void handleAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();

        ChatChannel channel = playerChannels.getOrDefault(player, BuiltInChatChannel.GLOBAL);

        if (isChatSilenced(channel)) {
            e.setCancelled(true);
            messages.sendMessage(player, "silenced");
            return;
        }

        e.setCancelled(true);

        isFilteredAsync(message).thenAccept(isFiltered -> {
            if (isFiltered) {
                messages.sendMessage(player, "inappropriate");
            } else {
                sendToChatChannel(channel, player, new Component(message));
            }
        });
    }

    private Set<Audience> getAudience(ChatChannel channel) {
        return channelAudiences.getOrDefault(channel, Collections.emptySet());
    }

    public void addPlayerToChannelAudience(ChatChannel channel, Player player) {
        channelAudiences.computeIfAbsent(channel, k -> new HashSet<>()).add(new PlayerAdapter(player));
    }

    public void removePlayerFromChannelAudience(ChatChannel channel, Player player) {
        Set<Audience> audience = channelAudiences.get(channel);
        if (audience != null) {
            audience.remove(new PlayerAdapter(player));
            if (audience.isEmpty()) {
                channelAudiences.remove(channel);
            }
        }
    }

    @Override
    public void setUp() {
        messages = ServerModuleManager.getInstance().getRegisteredModule(MessageModule.class);

        setAudienceFunction(BuiltInChatChannel.GLOBAL, player -> getDefaultAudience());
        setChatRenderer(BuiltInChatChannel.GLOBAL, (player, message) ->
                new Component(player.getName() + " [GLOBAL]: " + message.getText())
        );

        Bukkit.getOnlinePlayers().forEach(player -> addPlayerToChannelAudience(BuiltInChatChannel.GLOBAL, player));

        this.listener = new ChatListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void teardown() {
        audienceFunctions.clear();
        renderers.clear();
        playerChannels.clear();
        silencedChannels.clear();
        channelAudiences.clear();
    }

    private Set<Audience> getDefaultAudience() {
        return Bukkit.getOnlinePlayers().stream()
                .map(PlayerAdapter::new)
                .collect(Collectors.toSet());
    }
}
