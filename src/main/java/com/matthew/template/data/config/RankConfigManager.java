package com.matthew.template.data.config;

import com.matthew.template.data.config.framework.ConfigManager;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.serializer.Serializer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RankConfigManager extends ConfigManager {

    private final File rankFile;
    private final Serializer serializer;


    public RankConfigManager(final JavaPlugin plugin) throws IOException, InvalidConfigurationException {
        super();
        final String path = "ranks.yml";
        this.rankFile = new File(plugin.getDataFolder(), path);
        this.serializer = new Serializer();

        plugin.saveResource(path, false);

        loadConfig();
    }

    private void loadConfig() throws IOException {
        List<Rank> ranks = serializer.deserializeFromYamlFile(this.rankFile, Rank.class);
        if (ranks != null) {
            for (Rank rank : ranks) {
                module.addRank(rank);
            }
        }
    }

    @Override
    public void save() {

    }

    @Override
    public void reload() throws IOException {
        //module.clearRanks();
        loadConfig();
    }

    @Override
    public void destroy() {

    }
}
