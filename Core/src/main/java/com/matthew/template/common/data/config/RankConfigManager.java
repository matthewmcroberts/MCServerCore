package com.matthew.template.common.data.config;

import com.matthew.template.common.data.config.framework.ConfigManager;
import com.matthew.template.common.modules.ranks.dto.RankDTO;
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

        List<RankDTO> rankDTOS = serializer.deserializeFromYamlFile(this.rankFile, RankDTO.class);

        for (RankDTO rankDTO : rankDTOS) {
            if (!rankDTO.hasAllProperties()) {
                Bukkit.getLogger().severe("Failed to load " + rankDTO.getName() + " rank. Missing property/properties in ranks.yml");
                continue; //do not load rank
            }

            module.addRank(rankDTO);
        }
    }

    private String getDefaultYamlString() {
        List<RankDTO> defaultRankDTOS = new ArrayList<>();

        defaultRankDTOS.add(new RankDTO("OWNER", "&c", "&e", "[OWNER]", false, true, Collections.singletonList("rank.use")));
        defaultRankDTOS.add(new RankDTO("ADMIN", "&c", "&e", "[ADMIN]", false, true, Collections.singletonList("rank.use")));
        defaultRankDTOS.add(new RankDTO("MEMBER", "&7", "&f", "[MEMBER]", true, false, Collections.emptyList()));

        StringBuilder yamlBuilder = new StringBuilder();
        for (int i = 0; i < defaultRankDTOS.size(); i++) {
            RankDTO rankDTO = defaultRankDTOS.get(i);
            yamlBuilder.append("name: \"").append(rankDTO.getName()).append("\"\n");
            yamlBuilder.append("color: \"").append(rankDTO.getColor()).append("\"\n");
            yamlBuilder.append("chatColor: \"").append(rankDTO.getChatColor()).append("\"\n");
            yamlBuilder.append("prefix: \"").append(rankDTO.getPrefix()).append("\"\n");
            yamlBuilder.append("isDefault: ").append(rankDTO.isDefault()).append("\n");
            yamlBuilder.append("isStaff: ").append(rankDTO.isStaff()).append("\n");
            yamlBuilder.append("permissions:\n");
            for (String permission : rankDTO.getPermissions()) {
                yamlBuilder.append("  - \"").append(permission).append("\"\n");
            }
            if (i < defaultRankDTOS.size() - 1) {
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
