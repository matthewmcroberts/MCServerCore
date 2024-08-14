package com.matthew.template.bukkit.modules.chat.wrapper;

import com.matthew.template.bukkit.modules.chat.Component;
import com.matthew.template.bukkit.modules.chat.api.Audience;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

@Getter
public class PlayerAdapter implements Audience {
    private final UUID playerId;

    public PlayerAdapter(Player player) {
        this.playerId = player.getUniqueId();
    }

    @Override
    public void sendMessage(Component message) {
        Player player = Bukkit.getPlayer(playerId);
        if (player != null && player.isOnline()) {
            player.sendMessage(message.getText());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerAdapter that = (PlayerAdapter) o;
        return Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }
}
