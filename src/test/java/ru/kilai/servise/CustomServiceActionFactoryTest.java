package ru.kilai.servise;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomServiceActionFactoryTest {
    private final Flux<Integer> INPUT_DATA = Flux.just(1, 2, 3);

    @Test
    void createAction() {
        ServiceActionFactory<Integer, Integer> factory = new CustomServiceActionFactory<>();

        var result = factory.createAction(INPUT_DATA, item -> Flux.just(item - 1))
                .execute().collectList().block();

        assertNotNull(result);
        result.forEach(item -> assertEquals(result.indexOf(item), item));
    }
}