package com.matthew.template.api;

public interface ServerModule {

    /**
     * Sets up the module. (Currently empty)
     */
    void setUp();

    /**
     * Tears down any additional allocated resources
     */
    void teardown();
}
