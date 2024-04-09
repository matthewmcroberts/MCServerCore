package com.matthew.template.data.config.framework;

public abstract class ConfigManager {

    public abstract void save();

    public abstract void reload();

    public abstract void destroy();

    //implement logic for copyFile, isNewFile, getKeys, hasKey, setString, hasString, getInt, setInt, hasBool, setBool, etc...
}
