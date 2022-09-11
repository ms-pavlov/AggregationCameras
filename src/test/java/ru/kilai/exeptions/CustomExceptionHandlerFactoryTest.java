package ru.kilai.exeptions;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import ru.kilai.exeptions.handlers.ExceptionHandler;
import ru.kilai.servise.handlers.RequestHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomExceptionHandlerFactoryTest {
    private static final String TEST = "TEST";


    @Test
    void getHandler() {

        ExceptionHandler<String, String> right = (handler, o) -> Flux.just(TEST);
        RequestHandler<String, String> wrong = s -> {
            throw new RuntimeException();
        };

        var handlerMap = new ExceptionHandlerMapImpl<String, String>();
        handlerMap.putHandler(RuntimeException.class, wrong.getClass(), right);


        var handlerFactory = new CustomExceptionHandlerFactory<>(handlerMap);

        assertThrows(RuntimeException.class, () -> wrong.apply(TEST));

        var result = handlerFactory.getHandler(RuntimeException.class, wrong).apply(wrong, TEST).blockFirst();

        assertEquals(TEST, result);
    }
}