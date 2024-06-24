package com.matthew.template.test;

import com.matthew.template.common.apis.ServerModule;
import com.matthew.template.common.modules.manager.ServerModuleManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServerModuleManagerTest {

    private ServerModuleManager serverModuleManager;

    @BeforeEach
    void setUp() {
        serverModuleManager = ServerModuleManager.getInstance();
        // Clear the registered modules before each test to ensure a clean slate
        serverModuleManager.getRegisteredModules().clear();
    }

    @Test
    void testSingletonInstance() {
        ServerModuleManager instance1 = ServerModuleManager.getInstance();
        ServerModuleManager instance2 = ServerModuleManager.getInstance();
        assertSame(instance1, instance2, "Instances should be the same (singleton pattern)");
    }

    @Test
    void testRegisterModule() {
        ServerModule mockModule = mock(ServerModule.class);
        serverModuleManager.registerModule(mockModule);
        assertTrue(serverModuleManager.getRegisteredModules().contains(mockModule), "Module should be registered");
    }

    @Test
    void testGetRegisteredModule() {
        class TestModule implements ServerModule {
            @Override
            public void setUp() {}
            @Override
            public void teardown() {}
        }
        TestModule testModule = new TestModule();
        serverModuleManager.registerModule(testModule);
        TestModule retrievedModule = serverModuleManager.getRegisteredModule(TestModule.class);
        assertSame(testModule, retrievedModule, "Retrieved module should be the same as the registered module");
    }

    @Test
    void testGetRegisteredModule_NotFound() {
        ServerModule nonExistentModule = serverModuleManager.getRegisteredModule(ServerModule.class);
        assertNull(nonExistentModule, "Should return null when no module of the given class is registered");
    }

    @Test
    void testSetUp() {
        ServerModule mockModule1 = mock(ServerModule.class);
        ServerModule mockModule2 = mock(ServerModule.class);
        serverModuleManager.registerModule(mockModule1);
        serverModuleManager.registerModule(mockModule2);

        serverModuleManager.setUp();

        verify(mockModule1, times(1)).setUp();
        verify(mockModule2, times(1)).setUp();
    }

    @Test
    void testTeardown() {
        ServerModule mockModule1 = mock(ServerModule.class);
        ServerModule mockModule2 = mock(ServerModule.class);
        serverModuleManager.registerModule(mockModule1);
        serverModuleManager.registerModule(mockModule2);

        serverModuleManager.teardown();

        verify(mockModule1, times(1)).teardown();
        verify(mockModule2, times(1)).teardown();
    }
}
