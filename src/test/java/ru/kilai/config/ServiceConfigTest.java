package ru.kilai.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceConfigTest {
    private static final String PATH = "./where/config.ini";

    @Test
    void getHost() {
        assertEquals("localhost", new ServiceConfig(PATH).getHost());
        assertEquals("127.0.0.1", new ServiceConfig().getHost());
    }

    @Test
    void getPort() {
        assertEquals(8080, new ServiceConfig(PATH).getPort());
        assertEquals(8090, new ServiceConfig().getPort());
    }

    @Test
    void getServerEventPoolSize() {
        assertEquals(2, new ServiceConfig(PATH).getServerEventPoolSize());
        assertEquals(3, new ServiceConfig().getServerEventPoolSize());
    }

    @Test
    void getClientEventPoolSize() {
        assertEquals(2, new ServiceConfig(PATH).getClientEventPoolSize());
        assertEquals(4, new ServiceConfig().getClientEventPoolSize());
    }

    @Test
    void getHandlerPoolSize() {
        assertEquals(2, new ServiceConfig(PATH).getHandlerPoolSize());
        assertEquals(5, new ServiceConfig().getHandlerPoolSize());
    }

    @Test
    void getRetryDelay() {
        assertEquals(0, new ServiceConfig(PATH).getRetryDelay());
        assertEquals(2000, new ServiceConfig().getRetryDelay());
    }


    @Test
    void checkBadPath() {
        assertThrows(RuntimeException.class, () -> new ServiceConfig("./"));
    }
}