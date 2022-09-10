package ru.kilai.servise.handlers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestHandlerTest {
    private static final String PING = "ping-";
    private static final String PONG = "pong";
    @Test
    void andThen() {
        RequestHandler<String, String> handler = Flux::just;

        RequestHandler<Flux<String>, String> handler1 = flux -> flux.flatMap(s -> Flux.just(PING+s));

        var results = handler.andThen(handler1).apply(PONG).collectList().block();

        assertNotNull(results);
        assertEquals(1, results.size());
        results.forEach(item -> assertEquals(PING+PONG, item));
    }
}