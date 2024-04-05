package com.matthew.template.modules.storage.cache;

import com.matthew.template.modules.manager.ServerModuleManager;

public final class Cache {

    private static Cache instance;

    public static Cache getInstance() {
        if(instance == null) {
            instance = new Cache();
        }
        return instance;
    }


}
