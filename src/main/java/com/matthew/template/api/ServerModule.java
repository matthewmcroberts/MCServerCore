package com.matthew.template.api;

import org.jetbrains.annotations.NotNull;

public interface ServerModule {

    @NotNull
    void setUp();

    @NotNull
    void teardown();
}
