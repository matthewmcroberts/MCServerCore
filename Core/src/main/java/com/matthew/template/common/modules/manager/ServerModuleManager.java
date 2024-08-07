package com.matthew.template.common.modules.manager;

import com.matthew.template.common.apis.ServerModule;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
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

    public <T extends ServerModule> T getRegisteredModule(Class<T> clazz) {
        for(ServerModule module: registeredModules) {
            if(clazz.isInstance(module)) {
                return clazz.cast(module);
            }
        }
        return null;
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
