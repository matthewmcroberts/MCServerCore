package com.matthew.template.common.serializer.adapters;

import com.google.gson.*;
import com.matthew.template.common.apis.JsonSerializable;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import com.matthew.template.common.modules.player.data.PlayerData;
import com.matthew.template.common.modules.ranks.RankModule;
import com.matthew.template.common.modules.ranks.data.RankData;

import java.lang.reflect.Type;
import java.util.UUID;

public final class PlayerSerializer implements JsonSerializable<PlayerData> {

    private final RankModule rankModule;

    public PlayerSerializer() {
        this.rankModule = ServerModuleManager.getInstance().getRegisteredModule(RankModule.class);
    }

    @Override
    public PlayerData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.getAsJsonPrimitive("name").getAsString();
        UUID uuid = UUID.fromString(jsonObject.getAsJsonPrimitive("uuid").getAsString());
        String rankName = jsonObject.getAsJsonPrimitive("rank").getAsString();
        RankData rankData = rankModule.getRank(rankName);
        String chatColor = jsonObject.getAsJsonPrimitive("chatColor").getAsString();
        boolean isStaff = jsonObject.getAsJsonPrimitive("isStaff").getAsBoolean();
        long playTime = jsonObject.getAsJsonPrimitive("playTime").getAsLong();

        return new PlayerData(name, uuid, rankData, chatColor, isStaff, playTime);
    }

    @Override
    public JsonElement serialize(PlayerData playerData, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("name", new JsonPrimitive(playerData.getName()));
        result.add("uuid", new JsonPrimitive(playerData.getUuid().toString()));
        result.add("rank", new JsonPrimitive(playerData.getRankData().getName()));
        result.add("chatColor", new JsonPrimitive(playerData.getChatColor()));
        result.add("isStaff", new JsonPrimitive(playerData.isStaff()));
        result.add("playTime", new JsonPrimitive(playerData.getPlayTime()));
        return result;
    }
}
