package com.matthew.template.common.apis;

public interface ServerModule {

    /**
     * Sets up the module.
     */
    void setUp();

    /**
     * Tears down any additional allocated resources
     */
    void teardown();
}
