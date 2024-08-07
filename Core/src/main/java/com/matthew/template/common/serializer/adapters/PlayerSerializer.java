package com.matthew.template.common.serializer.adapters;

import com.google.gson.*;
import com.matthew.template.common.apis.JsonSerializable;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.dto.PlayerDTO;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.ranks.dto.RankDTO;

import java.lang.reflect.Type;
import java.util.UUID;

public final class PlayerSerializer implements JsonSerializable<PlayerDTO> {

    private final RankModule rankModule;

    public PlayerSerializer() {
        this.rankModule = ServerModuleManager.getInstance().getRegisteredModule(RankModule.class);
    }

    @Override
    public PlayerDTO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.getAsJsonPrimitive("name").getAsString();
        UUID uuid = UUID.fromString(jsonObject.getAsJsonPrimitive("uuid").getAsString());
        String rankName = jsonObject.getAsJsonPrimitive("rank").getAsString();
        RankDTO rankDTO = rankModule.getRank(rankName);
        String chatColor = jsonObject.getAsJsonPrimitive("chatColor").getAsString();
        boolean isStaff = jsonObject.getAsJsonPrimitive("isStaff").getAsBoolean();
        long playTime = jsonObject.getAsJsonPrimitive("playTime").getAsLong();

        return new PlayerDTO(name, uuid, rankDTO, chatColor, isStaff, playTime);
    }

    @Override
    public JsonElement serialize(PlayerDTO playerDto, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("name", new JsonPrimitive(playerDto.getName()));
        result.add("uuid", new JsonPrimitive(playerDto.getUuid().toString()));
        result.add("rank", new JsonPrimitive(playerDto.getRankDTO().getName()));
        result.add("chatColor", new JsonPrimitive(playerDto.getChatColor()));
        result.add("isStaff", new JsonPrimitive(playerDto.isStaff()));
        result.add("playTime", new JsonPrimitive(playerDto.getPlayTime()));
        return result;
    }
}
