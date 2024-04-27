package com.matthew.template.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matthew.template.api.JsonSerializable;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for serializing and deserializing objects to/from YAML and JSON formats.
 */
public final class Serializer {

    /**
     * Default constructor.
     */
    public Serializer() {}

    /**
     * Deserialize a list of objects from a YAML file.
     *
     * @param yamlFile The YAML file to deserialize.
     * @param clazz The class type of the objects being deserialized.
     * @param <T> The generic type of the objects being deserialized.
     * @return A list of deserialized objects.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public <T> List<T> deserializeFromYamlFile(final File yamlFile, final Class<T> clazz) throws IOException {
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

    /**
     * Deserialize an object from a JSON string.
     *
     * @param jsonString The JSON string to deserialize.
     * @param clazz The class type of the object being deserialized.
     * @param adapterClazz The class type of the adapter implementing JsonSerializable interface.
     * @param <T> The generic type of the object being deserialized.
     * @return The deserialized object.
     */
    public <T> T deserializeObjectFromJsonString(final String jsonString, final Class<T> clazz, final Class<?> adapterClazz) {
        validateAdapter(adapterClazz);

        JsonSerializable<?> adapterInstance = instantiateAdapter(adapterClazz);
        Gson gson = createGsonWithAdapter(clazz, adapterInstance);

        return gson.fromJson(jsonString, clazz);
    }

    /**
     * Serialize an object to a JSON string.
     *
     * @param object The object to serialize.
     * @param clazz The class type of the object being serialized.
     * @param adapterClazz The class type of the adapter implementing JsonSerializable interface.
     * @param <T> The generic type of the object being serialized.
     * @return The JSON string representing the serialized object.
     */
    public <T> String serializeObjectToJsonString(final T object, final Class<T> clazz, final Class<?> adapterClazz) {
        validateAdapter(adapterClazz);

        JsonSerializable<?> adapterInstance = instantiateAdapter(adapterClazz);
        Gson gson = createGsonWithAdapter(clazz, adapterInstance);

        return gson.toJson(object);
    }

    /**
     * Validate if the provided adapter class implements the JsonSerializable interface.
     *
     * @param adapterClazz The adapter class to validate.
     */
    private void validateAdapter(final Class<?> adapterClazz) {
        if (!JsonSerializable.class.isAssignableFrom(adapterClazz)) {
            throw new IllegalArgumentException("The provided adapter class does not implement the JsonSerializable interface");
        }
    }

    /**
     * Instantiate an adapter class implementing the JsonSerializable interface.
     *
     * @param adapterClazz The adapter class to instantiate.
     * @return The instantiated adapter instance.
     */
    private JsonSerializable<?> instantiateAdapter(final Class<?> adapterClazz) {
        try {
            return (JsonSerializable<?>) adapterClazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException("Failed to instantiate type adapter", e);
        }
    }

    /**
     * Create a Gson instance with the provided adapter for serialization/deserialization.
     *
     * @param clazz The class type for Gson serialization/deserialization.
     * @param adapterInstance The adapter instance implementing JsonSerializable interface.
     * @return The Gson instance configured with the provided adapter.
     */
    private Gson createGsonWithAdapter(final Class<?> clazz, final JsonSerializable<?> adapterInstance) {
        return new GsonBuilder().registerTypeAdapter(clazz, adapterInstance).create();
    }
}
