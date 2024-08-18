package com.matthew.template.bukkit.modules.messages;

import com.matthew.template.bukkit.utils.ChatColorUtils;
import com.matthew.template.common.apis.ServerModule;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MessageModule implements ServerModule {

    private final JavaPlugin plugin;
    private Map<String, String> cache;
    private String prefix;

    public Component buildMessage(String key, Object... args) {
        String message = cache.get(key);

        if (message == null) {
            plugin.getLogger().warning("Message key '" + key + "' not found");
            return Component.text("");
        }

        if ((args != null) && (args.length > 0)) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("<" + i + ">", String.valueOf(args[i]));
            }
        }

        return ChatColorUtils.parseColors(Component.text(prefix + message));
    }

    public void buildThenRunMessage(String key, Consumer<Component> callback) {
        callback.accept(this.buildMessage(key, (Object) null));
    }

    public void buildThenRunMessage(String key, Consumer<Component> callback, Object... args) {
        callback.accept(this.buildMessage(key, args));
    }

    public void sendMessage(Player player, String key, Object... args) {
        Component message = buildMessage(key, args);
        player.sendMessage(message);
    }

    @Override
    public void setUp() {
        File configFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!configFile.exists()) {
            try (InputStream inputStream = plugin.getResource("messages.yml")) {
                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    plugin.getLogger().info("Copied messages.yml from resources to plugin folder.");
                } else {
                    plugin.getLogger().warning("Default messages.yml not found in resources.");
                }
            } catch (IOException e) {
                plugin.getLogger().warning("Error copying messages.yml from resources: " + e.getMessage());
            }
        }

        try {
            Yaml yaml = new Yaml();
            cache = yaml.load(Files.newBufferedReader(configFile.toPath()));
            prefix = cache.getOrDefault("prefix", ""); // Load the prefix from messages.yml
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        } catch (IOException e) {
            plugin.getLogger().warning("Error loading messages.yml: " + e.getMessage());
        }

        if (cache != null) {
            plugin.getLogger().info("Loaded messages from messages.yml: " + cache.keySet());
        }
    }

    @Override
    public void teardown() {
        cache.clear();
    }
}
