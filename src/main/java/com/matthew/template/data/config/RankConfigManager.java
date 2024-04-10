package com.matthew.template.data.config;

import com.matthew.template.data.config.framework.ConfigManager;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RankConfigManager extends ConfigManager {

    private final JavaPlugin plugin;

    private final ServerModuleManager moduleManager;

    private final DataStorageModule module;

    private final String path;

    //Use constructor to load file
    public RankConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(DataStorageModule.class);
        this.path = "ranks.yml";

        plugin.saveResource(path, false);

        File rankFile = new File(plugin.getDataFolder(), path);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(rankFile);
        Set<String> keys = config.getKeys(false);

        for (String key : keys) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section == null) {
                System.out.println("null");
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

    }

    @Override
    public void reload() {

    }

    @Override
    public void destroy() {

    }
}
