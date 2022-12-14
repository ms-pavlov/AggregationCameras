package ru.kilai.servise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomServiceActionTest {

    private Stream<Integer> testData;

    @BeforeEach
    void setUo() {
        testData = Stream.of(1, 2, 3);
    }


    @Test
    void checkBuild() {
        var task = new CustomServiceAction<>(Flux.fromStream(testData), i -> Flux.just(i - 1));

        var result = task.execute().collectList().block();
        assertNotNull(result);
        for (Integer number : result) {
            assertEquals(result.indexOf(number), number);
        }
    }

}