package ru.kilai.servise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import ru.kilai.servise.CustomServiceAction;

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

    @Test
    void andThen() {
        var task = new CustomServiceAction<>(Flux.fromStream(testData), i -> Flux.just(i - 1))
                .andThen(i -> Flux.just("number" + i));

        var result = task.execute().collectList().block();
        assertNotNull(result);
        for (String item : result) {
            assertEquals("number" + result.indexOf(item), item);
        }
    }

}