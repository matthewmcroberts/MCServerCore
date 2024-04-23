package com.matthew.template.serializer;

import com.google.gson.Gson;
import com.matthew.template.modules.player.structure.PlayerData;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer {


    public Serializer() {
    }

    public <T> List<T> deserializeFromYamlFile(File yamlFile, Class<T> clazz) throws IOException {
        List<T> objects = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(yamlFile)) {
            LoaderOptions options = new LoaderOptions();
            options.setAllowDuplicateKeys(false);
            Constructor constructor = new Constructor(clazz, options);
            Yaml yaml = new Yaml(constructor);

            Iterable<Object> yamlObjects = yaml.loadAll(inputStream);

            for (Object obj : yamlObjects) {
                objects.add(clazz.cast(obj));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + yamlFile.getAbsolutePath(), e);
        } catch (YAMLException e) {
            throw new IllegalArgumentException("Error parsing YAML file: " + yamlFile.getAbsolutePath(), e);
        }
        return objects;
    }

    public <T> T deserializeFromJsonString(String jsonString, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }

    public <T> String serializeToJsonString(PlayerData player) {
        Gson gson = new Gson();
        return gson.toJson(player);
    }
}
