package com.matthew.template.modules.storage;

import com.matthew.template.api.ServerModule;
import com.matthew.template.modules.storage.cache.Cache;

public final class DataStorageModule implements ServerModule {

    Cache cache = Cache.getInstance();

    @Override
    public void setUp() {

    }

    @Override
    public void teardown() {

    }
}
