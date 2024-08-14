package com.matthew.template.bukkit.modules.chat.api;

import com.matthew.template.bukkit.modules.chat.Component;
import org.bukkit.entity.Player;

public interface ChatRenderer {
    Component render(Player player, Component message);
}
