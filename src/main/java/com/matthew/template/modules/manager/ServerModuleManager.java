package com.matthew.template.modules.manager;

import com.matthew.template.api.ServerModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ServerModuleManager implements ServerModule {

    private static ServerModuleManager instance;
    private final Set<ServerModule> registeredModules;
    private ServerModuleManager() {
        registeredModules = new HashSet<>();
    }

    public static ServerModuleManager getInstance() {
        if(instance == null) {
            instance = new ServerModuleManager();
        }
        return instance;
    }

    public ServerModuleManager registerModule(ServerModule module) {
        registeredModules.add(module);
        return this; //used for method chaining in NativePractice#onEnable
    }

    public ServerModule getRegisteredModule(Class<? extends ServerModule> clazz) {
        for(ServerModule module: registeredModules) {
            if(clazz.isInstance(module)) {
                return module;
            }
        }
        return null;
    }

    public Set<ServerModule> getRegisteredModules() {
        return this.registeredModules;
    }

    @Override
    public void setUp() {
        for(ServerModule module: registeredModules) {
            module.setUp();
        }
    }

    @Override
    public void teardown() {
        for(ServerModule module: registeredModules) {
            module.teardown();
        }
    }
}
