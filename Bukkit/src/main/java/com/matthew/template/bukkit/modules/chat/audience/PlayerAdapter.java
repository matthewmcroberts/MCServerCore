package com.matthew.template.bukkit.modules.chat.audience;

import com.matthew.template.bukkit.modules.chat.api.Audience;
import lombok.Getter;
import net.kyori.adventure.text.Component;
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
            player.sendMessage(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        PlayerAdapter adapter = (PlayerAdapter) o;
        return Objects.equals(playerId, adapter.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }
}
