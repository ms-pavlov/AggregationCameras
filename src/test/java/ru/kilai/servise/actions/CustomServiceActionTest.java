package ru.kilai.servise.actions;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomServiceActionTest {
    private static final Stream<Integer> TEST_DATA = Stream.of(1, 2, 3);


    @Test
    void checkBuild() {
        var task = new CustomServiceAction<>(Flux.fromStream(TEST_DATA), i -> Flux.just(i - 1));

        var result = task.execute().collectList().block();
        assertNotNull(result);
        for (Integer number : result) {
            assertEquals(result.indexOf(number), number);
        }
    }
}