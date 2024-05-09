package com.matthew.template.modules.player.permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.jetbrains.annotations.Nullable;

public class PlayerPermissible extends PermissibleBase {
    public PlayerPermissible(@Nullable Player player) {
        super(player);
    }
}
