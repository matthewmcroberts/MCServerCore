package com.matthew.template.modules.storage.cache;

import com.matthew.template.data.PlayerData;
import com.matthew.template.modules.ranks.structure.Rank;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public final class Cache {

    /*
    TODO: Make sure that the Cache is fully loaded before any modules are accessed
     */

    private static Cache instance;

    private final Set<Rank> ranks;
    private final Map<Rank, List<PermissionAttachment>> permissions;
    private final List<PlayerData> players;

    private Cache() {
        ranks = new HashSet<>();
        permissions = new HashMap<>();
        players = new ArrayList<>();
    }

    public static Cache getInstance() {
        if(instance == null) {
            instance = new Cache();
        }
        return instance;
    }


}
