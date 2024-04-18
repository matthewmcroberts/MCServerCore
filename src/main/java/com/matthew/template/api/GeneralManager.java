package com.matthew.template.api;

import org.jetbrains.annotations.NotNull;

public interface GeneralManager {

    @NotNull
    void register();

    @NotNull
    void unregister();
}
