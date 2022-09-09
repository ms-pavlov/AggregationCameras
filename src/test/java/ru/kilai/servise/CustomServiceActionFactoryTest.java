package ru.kilai.servise;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.kilai.config.SimplerThreadFactory;

import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

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