package com.matthew.template.common.modules.player.data;

import com.matthew.template.common.modules.ranks.data.RankData;
import lombok.*;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * The purpose of PlayerDTO is to keep track of data that will end up being stored or accessed in the database.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public final class PlayerData {

    private String name;
    private UUID uuid;
    @NonNull private RankData rankData;
    private String chatColor;
    private boolean isStaff;
    private long playTime;
    private boolean isModified;

    public PlayerData(final Player player, final RankData rankData, final long playTime) {
        this.name = player.getDisplayName();
        this.uuid = player.getUniqueId();
        this.rankData = rankData;
        this.isStaff = rankData.isStaff();
        this.chatColor = rankData.getChatColor();
        this.playTime = playTime;
        this.isModified = false;
    }

    public PlayerData(String name, UUID uuid, RankData rankData, String chatColor, boolean isStaff, long playTime) {
        this.name = name;
        this.uuid = uuid;
        this.rankData = rankData;
        this.chatColor = chatColor;
        this.isStaff = isStaff;
        this.playTime = playTime;
        this.isModified = false;
    }
}
