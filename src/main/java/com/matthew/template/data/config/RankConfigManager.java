package com.matthew.template.data.config;

import com.matthew.template.data.config.framework.ConfigManager;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.serializer.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.charset.StandardCharsets;
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

        Bukkit.getLogger().info(new String(Files.readAllBytes(rankFile.toPath()), StandardCharsets.UTF_8));

        List<Rank> ranks = serializer.deserializeFromYamlFile(this.rankFile, Rank.class);
        for (Rank rank : ranks) {
            module.addRank(rank);
        }
    }

    private String getDefaultYamlString() {
        List<Rank> defaultRanks = new ArrayList<>();

        defaultRanks.add(new Rank("OWNER", "&c", "&e", "[OWNER]", false, true, Collections.singletonList("rank.use")));
        defaultRanks.add(new Rank("ADMIN", "&c", "&e", "[ADMIN]", false, true, Collections.singletonList("rank.use")));
        defaultRanks.add(new Rank("MEMBER", "&7", "&f", "[MEMBER]", true, false, Collections.singletonList("")));

        StringBuilder yamlBuilder = new StringBuilder();
        for (int i = 0; i < defaultRanks.size(); i++) {
            Rank rank = defaultRanks.get(i);
            yamlBuilder.append("name: \"").append(rank.getName()).append("\"\n");
            yamlBuilder.append("color: \"").append(rank.getColor()).append("\"\n");
            yamlBuilder.append("chatColor: \"").append(rank.getChatColor()).append("\"\n");
            yamlBuilder.append("prefix: \"").append(rank.getPrefix()).append("\"\n");
            yamlBuilder.append("isDefault: ").append(rank.isDefault()).append("\n");
            yamlBuilder.append("isStaff: ").append(rank.isStaff()).append("\n");
            yamlBuilder.append("permissions:\n");
            for (String permission : rank.getPermissions()) {
                yamlBuilder.append("  - \"").append(permission).append("\"\n");
            }
            if (i < defaultRanks.size() - 1) {
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
