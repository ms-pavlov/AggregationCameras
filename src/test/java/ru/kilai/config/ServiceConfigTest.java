package ru.kilai.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceConfigTest {
    private static final String PATH = "./where/config.ini";

    @Test
    void getHost() {
        assertEquals("localhost", new ServiceConfig(PATH).getHost());
    }

    @Test
    void getPort() {
        assertEquals(8080, new ServiceConfig(PATH).getPort());
    }

    @Test
    void getServerEventPoolSize() {
        assertEquals(2, new ServiceConfig(PATH).getServerEventPoolSize());
    }

    @Test
    void getClientEventPoolSize() {
        assertEquals(2, new ServiceConfig(PATH).getClientEventPoolSize());
    }

    @Test
    void getHandlerPoolSize() {
        assertEquals(2, new ServiceConfig(PATH).getHandlerPoolSize());
    }

    @Test
    void getRetryDelay() {
        assertEquals(0, new ServiceConfig(PATH).getRetryDelay());
    }


    @Test
    void checkBadPath() {
        assertThrows(RuntimeException.class, () -> new ServiceConfig("./"));
    }
}