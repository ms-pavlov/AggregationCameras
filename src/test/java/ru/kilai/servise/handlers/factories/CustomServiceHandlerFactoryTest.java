package ru.kilai.servise.handlers.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;
import ru.kilai.servise.handlers.LogWrapper;
import ru.kilai.servise.handlers.RequestHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomServiceHandlerFactoryTest {

    private CustomServiceHandlerFactory handlerFactory;

    @BeforeEach
    void setUp() {
        handlerFactory = new CustomServiceHandlerFactory(-100);
    }

    @Test
    void createPostParametersHandler() {
        assertDoesNotThrow(() -> handlerFactory.createPostParametersHandler());
        assertEquals(LogWrapper.class, handlerFactory.createPostParametersHandler().getClass());
    }

    @Test
    void createContentHttpRequestHandler() {
        assertDoesNotThrow(() -> handlerFactory.createContentHttpRequestHandler(mock(HttpClient.class)));
        assertEquals(LogWrapper.class, handlerFactory.createContentHttpRequestHandler(mock(HttpClient.class)).getClass());
    }
}