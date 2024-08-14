package com.matthew.template.bukkit.modules.chat.channels;

import com.matthew.template.bukkit.modules.chat.api.ChatChannel;
import lombok.Getter;

/*
Default built in chat channel "global"
 */
@Getter
public enum BuiltInChatChannel implements ChatChannel {
    GLOBAL("global");

    private final String identifier;

    BuiltInChatChannel(String identifier) {
        this.identifier = identifier;
    }
}
