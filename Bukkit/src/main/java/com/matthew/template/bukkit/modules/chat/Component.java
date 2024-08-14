package com.matthew.template.bukkit.modules.chat;

import lombok.Getter;

/*
Seems pointless now, but will be important for later implementations
 */
@Getter
public class Component {
    private final String text;

    public Component(String text) {
        this.text = text;
    }
}
