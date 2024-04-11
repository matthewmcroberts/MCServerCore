package com.matthew.template.serializer.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Map;

public abstract class Serializer {

    protected final ObjectMapper mapper;

    public Serializer() {
        this.mapper = new ObjectMapper();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> serialize(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, Map.class);
    }

    public abstract Rank deserialize();

    public abstract Rank deserializeFromYaml(YamlConfiguration config);

}
