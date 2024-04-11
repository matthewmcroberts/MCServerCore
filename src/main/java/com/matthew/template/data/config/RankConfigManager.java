package com.matthew.template.data.config;

import com.matthew.template.data.config.framework.ConfigManager;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RankConfigManager extends ConfigManager {

    private final JavaPlugin plugin;

    private final ServerModuleManager moduleManager;

    private final DataStorageModule module;

    private final File rankFile;

    private final YamlConfiguration config;

    //TODO: Setup ConfigManager
    public RankConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(DataStorageModule.class);
        String path = "ranks.yml";

        plugin.saveResource(path, false);

        this.rankFile = new File(plugin.getDataFolder(), path);
        this.config = YamlConfiguration.loadConfiguration(rankFile);

        loadConfig();

    }

    private void loadConfig() {
        Set<String> keys = config.getKeys(false);

        for (String key : keys) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section == null) {
                continue;
            }

            String inherits = section.getString("INHERITS");
            String color = section.getString("COLOR");
            String chatColor = section.getString("CHAT_COLOR");
            String prefix = section.getString("PREFIX");
            boolean isDefault = section.getBoolean("IS_DEFAULT");
            boolean isStaff = section.getBoolean("IS_STAFF");
            List<String> permissions = section.getStringList("PERMISSIONS");
            Set<String> perms = new HashSet<>(permissions);

            assert module != null;
            module.addRank(new Rank(key, color, chatColor, prefix, isDefault, isStaff, perms));
        }
    }

    @Override
    public void save() {
        final Set<Rank> ranks = module.getAllRanks();

        for (Rank rank : ranks) {
            String key = rank.getName();
            ConfigurationSection section = config.createSection(key);
            //section.set("INHERITS", rank.getInherits());
            section.set("COLOR", rank.getColor());
            section.set("CHAT_COLOR", rank.getChatColor());
            section.set("PREFIX", rank.getPrefix());
            section.set("IS_DEFAULT", rank.isDefault());
            section.set("IS_STAFF", rank.isStaff());
            section.set("PERMISSIONS", new ArrayList<>(rank.getPermissions()));
        }

        try {
            config.save(rankFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        module.clearRanks();

        loadConfig();
    }

    @Override
    public void destroy() {

    }
}
