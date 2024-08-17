package com.matthew.template.bukkit.modules.chat.api;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface ChatRenderer {
    Component render(Player player, Component message);
}
