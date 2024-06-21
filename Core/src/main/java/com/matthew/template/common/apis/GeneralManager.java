package com.matthew.template.common.apis;

import org.jetbrains.annotations.NotNull;

public interface GeneralManager {

    /**
     * Registers the general manager.
     * This method should handle the necessary steps to register the manager within the system.
     */
    @NotNull
    void register();

    /**
     * Unregisters the general manager.
     * This method should handle the necessary steps to unregister the manager from the system.
     */
    @NotNull
    void unregister();
}

