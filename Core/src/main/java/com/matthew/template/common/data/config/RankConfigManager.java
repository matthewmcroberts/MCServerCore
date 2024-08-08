package com.matthew.template.common.data.config;

import com.matthew.template.common.data.config.framework.ConfigManager;
import com.matthew.template.common.modules.ranks.data.RankData;
import com.matthew.template.common.serializer.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankConfigManager extends ConfigManager {

    private final String path;
    private final File rankFile;
    private final Serializer serializer;


    public RankConfigManager(final JavaPlugin plugin) throws IOException, InvalidConfigurationException {
        super();
        this.path = "ranks.yml";
        this.rankFile = new File(plugin.getDataFolder(), path);
        this.serializer = new Serializer();

        if (!this.rankFile.exists()) {
            plugin.saveResource(path, false);
        }

        loadConfig();
    }

    private void loadConfig() throws IOException {

        if (this.rankFile.length() == 0) {
            Files.write(rankFile.toPath(), getDefaultYamlString().getBytes(), StandardOpenOption.CREATE);
        }

        List<RankData> rankData = serializer.deserializeFromYamlFile(this.rankFile, RankData.class);

        for (RankData rank : rankData) {
            if (!rank.hasAllProperties()) {
                Bukkit.getLogger().severe("Failed to load " + rank.getName() + " rank. Missing property/properties in ranks.yml");
                continue; //do not load rank
            }

            module.addRank(rank);
        }
    }

    private String getDefaultYamlString() {
        List<RankData> defaultRankData = new ArrayList<>();

        defaultRankData.add(new RankData("OWNER", "&c", "&e", "[OWNER]", false, true, Collections.singletonList("rank.use")));
        defaultRankData.add(new RankData("ADMIN", "&c", "&e", "[ADMIN]", false, true, Collections.singletonList("rank.use")));
        defaultRankData.add(new RankData("MEMBER", "&7", "&f", "[MEMBER]", true, false, Collections.emptyList()));

        StringBuilder yamlBuilder = new StringBuilder();
        for (int i = 0; i < defaultRankData.size(); i++) {
            RankData rankData = defaultRankData.get(i);
            yamlBuilder.append("name: \"").append(rankData.getName()).append("\"\n");
            yamlBuilder.append("color: \"").append(rankData.getColor()).append("\"\n");
            yamlBuilder.append("chatColor: \"").append(rankData.getChatColor()).append("\"\n");
            yamlBuilder.append("prefix: \"").append(rankData.getPrefix()).append("\"\n");
            yamlBuilder.append("isDefault: ").append(rankData.isDefault()).append("\n");
            yamlBuilder.append("isStaff: ").append(rankData.isStaff()).append("\n");
            yamlBuilder.append("permissions:\n");
            for (String permission : rankData.getPermissions()) {
                yamlBuilder.append("  - \"").append(permission).append("\"\n");
            }
            if (i < defaultRankData.size() - 1) {
                yamlBuilder.append("\n---\n");
            }
        }

        return yamlBuilder.toString();
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
