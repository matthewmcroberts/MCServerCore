package com.matthew.template.common.modules.player.dto;

import com.matthew.template.common.modules.ranks.dto.RankDTO;
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
public final class PlayerDTO {

    private String name;
    private UUID uuid;
    @NonNull private RankDTO rankDTO;
    private String chatColor;
    private boolean isStaff;
    private long playTime;
    private boolean isModified;

    public PlayerDTO(final Player player, final RankDTO rankDTO, final long playTime) {
        this.name = player.getDisplayName();
        this.uuid = player.getUniqueId();
        this.rankDTO = rankDTO;
        this.isStaff = rankDTO.isStaff();
        this.chatColor = rankDTO.getChatColor();
        this.playTime = playTime;
        this.isModified = false;
    }

    public PlayerDTO(String name, UUID uuid, RankDTO rankDTO, String chatColor, boolean isStaff, long playTime) {
        this.name = name;
        this.uuid = uuid;
        this.rankDTO = rankDTO;
        this.chatColor = chatColor;
        this.isStaff = isStaff;
        this.playTime = playTime;
        this.isModified = false;
    }
}
