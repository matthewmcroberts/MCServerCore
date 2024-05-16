package com.matthew.template.common.apis;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * MARKER INTERFACE
 */
public interface JsonSerializable<T> extends JsonSerializer<T>, JsonDeserializer<T> {}
