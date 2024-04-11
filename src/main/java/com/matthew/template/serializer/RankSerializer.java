package com.matthew.template.serializer;

import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.serializer.framework.Serializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Set;

public class RankSerializer extends Serializer {


    public RankSerializer() {
        super();
    }

    @Override
    public Rank deserialize() {
        return null;
    }

    public Rank deserializeFromYaml(YamlConfiguration config) {
        Set<String> keys = config.getKeys(false);

        for(String key: keys) {
            ConfigurationSection section = config.getConfigurationSection(key);
            Map<String, Object> rankData = config.getValues(true);
            Rank rank = mapper.convertValue(rankData, Rank.class);
            rank.setName(rankName);
            return rank;

        }
    }
}
