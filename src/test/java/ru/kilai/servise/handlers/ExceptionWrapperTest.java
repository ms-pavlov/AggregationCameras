package ru.kilai.servise.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import ru.kilai.client.CustomContentHttpClient;
import ru.kilai.exeptions.CustomExceptionHandlerFactory;
import ru.kilai.exeptions.ExceptionHandlerFactory;
import ru.kilai.exeptions.ExceptionHandlerMap;
import ru.kilai.exeptions.ExceptionHandlerMapImpl;
import ru.kilai.exeptions.handlers.ExceptionHandler;
import ru.kilai.servise.handlers.factories.CustomServiceHandlerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionWrapperTest {
    private static final Logger log = LoggerFactory.getLogger(ExceptionWrapperTest.class);
    private static final String TEST = "TEST";

    private ExceptionHandlerMap<String, String> handlerMap;

    @BeforeEach
    void setUp() {
        handlerMap = new ExceptionHandlerMapImpl<>();
    }

    @Test
    void checkFluxErrorApply() {
        ExceptionHandler<String, String> right = (handler, o) -> {
            assertEquals(TEST, o);
            return Flux.just(TEST + "next");
        };
        RequestHandler<String, String> first = s -> Flux.just(s + "next");
        RequestHandler<Flux<String>, String> bad = s -> Flux.error(RuntimeException::new);
        RequestHandler<Flux<String>, String> next = s -> s;

        var handler = first.andThen(bad).andThen(next);

        handlerMap.putHandler(RuntimeException.class, handler.getClass(), right);
        var exceptionFactory = new CustomExceptionHandlerFactory<>(handlerMap);

        var result = new ExceptionWrapper<>(handler, exceptionFactory)
                .apply(TEST).blockFirst();

        assertEquals(TEST + "next", result);
    }

    @Test
    void checkFailApply() {
        RequestHandler<String, String> first = s -> Flux.just(s + "next");
        RequestHandler<Flux<String>, String> bad = s -> {
            throw new RuntimeException();
        };
        RequestHandler<Flux<String>, String> next = s -> s;

        var handler = first.andThen(bad).andThen(next);

        var exceptionFactory = new CustomExceptionHandlerFactory<>(handlerMap);

        assertThrows(RuntimeException.class, () -> new ExceptionWrapper<>(handler, exceptionFactory).apply(TEST));
    }


    @Test
    void checkHandlerName() {
        var serviceHandlerFactory = new CustomServiceHandlerFactory(1);
        var httpClient = new CustomContentHttpClient(1);

        var handler = new GetParameters()
                .andThen(serviceHandlerFactory.createContentHttpRequestHandler(httpClient))
                .andThen(serviceHandlerFactory.createUrlListParser())
                .andThen(serviceHandlerFactory.createCameraInfoHttpRequest(httpClient))
                .andThen(serviceHandlerFactory.createCameraInfoToJsonString());

        log.debug("handler Name {}", handler.getClass());
    }

}