package com.matthew.template.bukkit.utils;


import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.HashMap;
import java.util.Map;

public final class ChatColorUtils {

    public static final Map<String, String> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("red", "#FF5555");
        COLOR_MAP.put("green", "#55FF55");
        COLOR_MAP.put("blue", "#5555FF");
        COLOR_MAP.put("yellow", "#FFFF55");
        COLOR_MAP.put("aqua", "#55FFFF");
        COLOR_MAP.put("light_purple", "#FF55FF");
        COLOR_MAP.put("white", "#FFFFFF");
        COLOR_MAP.put("black", "#000000");
        COLOR_MAP.put("gray", "#AAAAAA");
    }

    private ChatColorUtils() {}

    public static TextColor getColorFromName(String colorName) {
        String hexColor = COLOR_MAP.get(colorName.toLowerCase());
        if (hexColor != null) {
            return TextColor.fromHexString(hexColor);
        }
        return NamedTextColor.WHITE;
    }
}
