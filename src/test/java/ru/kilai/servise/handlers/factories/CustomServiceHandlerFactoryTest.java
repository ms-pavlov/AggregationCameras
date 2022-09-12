package ru.kilai.servise.handlers.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kilai.client.ContentHttpClient;
import ru.kilai.servise.handlers.wrappers.LogWrapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CustomServiceHandlerFactoryTest {

    private CustomServiceHandlerFactory handlerFactory;

    @BeforeEach
    void setUp() {
        handlerFactory = new CustomServiceHandlerFactory(-100);
    }

    @Test
    void createContentHttpRequestHandler() {
        assertDoesNotThrow(() -> handlerFactory.createContentHttpRequestHandler(mock(ContentHttpClient.class)));
        assertEquals(LogWrapper.class, handlerFactory.createContentHttpRequestHandler(mock(ContentHttpClient.class)).getClass());
    }

    @Test
    void checkConstructor() {
        assertDoesNotThrow(() -> new CustomServiceHandlerFactory());
    }

    @Test
    void createUrlListParser() {
        assertDoesNotThrow(() -> handlerFactory.createUrlListParser());
        assertEquals(LogWrapper.class, handlerFactory.createUrlListParser().getClass());

    }

    @Test
    void createCameraInfoToJsonString() {
        assertDoesNotThrow(() -> handlerFactory.createCameraInfoToJsonString());
        assertEquals(LogWrapper.class, handlerFactory.createCameraInfoToJsonString().getClass());
    }

    @Test
    void createCameraInfoHttpRequest() {
        assertDoesNotThrow(() -> handlerFactory.createCameraInfoHttpRequest(mock(ContentHttpClient.class)));
        assertEquals(LogWrapper.class, handlerFactory.createCameraInfoHttpRequest(mock(ContentHttpClient.class)).getClass());
    }

    @Test
    void createGetParameters() {
        assertDoesNotThrow(() -> handlerFactory.createGetParameters());
        assertEquals(LogWrapper.class, handlerFactory.createGetParameters().getClass());
    }

    @Test
    void createPostParameters() {
        assertDoesNotThrow(() -> handlerFactory.createPostParameters());
        assertEquals(LogWrapper.class, handlerFactory.createPostParameters().getClass());
    }
}