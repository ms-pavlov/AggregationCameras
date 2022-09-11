package ru.kilai.exeptions.handlers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import ru.kilai.servise.handlers.RequestHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RetryAggregationHandlerTest {
    private static final String TEST = "TEST";

    @Test
    void apply() {
        RequestHandler<Flux<String>, String> first = s -> s.map(s1 -> s1 + "next");
        RequestHandler<Flux<String>, String> bad = s -> {
            throw new RuntimeException();
        };
        var retry = new RetryAggregationHandler(first, 0);

        var result = retry.apply(bad, Flux.just(TEST)).collectList().block();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TEST + "next", result.get(0));
    }
}