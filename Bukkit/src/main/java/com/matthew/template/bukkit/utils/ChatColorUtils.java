package com.matthew.template.bukkit.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static Component parseColors(Component message) {
        String text = LegacyComponentSerializer.legacySection().serialize(message);
        Pattern pattern = Pattern.compile("<(\\w+)>(.*?)</\\1>");
        Matcher matcher = pattern.matcher(text);

        Component result = Component.empty();
        int lastEnd = 0;

        while (matcher.find()) {
            String colorName = matcher.group(1);
            String matchText = matcher.group(2);

            if (matcher.start() > lastEnd) {
                result = result.append(Component.text(text.substring(lastEnd, matcher.start())));
            }

            TextColor color = getColorFromName(colorName);
            result = result.append(Component.text(matchText, color));

            lastEnd = matcher.end();
        }

        if (lastEnd < text.length()) {
            result = result.append(Component.text(text.substring(lastEnd)));
        }

        return result;
    }
}
