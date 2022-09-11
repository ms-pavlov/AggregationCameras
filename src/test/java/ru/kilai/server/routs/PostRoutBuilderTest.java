package ru.kilai.server.routs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.util.AbstractServiceTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class PostRoutBuilderTest {
    private static final String TEST_DATA = "same test data";
    private static final String UPI = "/";

    private PostBindStrategy bindStrategy;

    @BeforeEach
    void setUp() {
        this.bindStrategy = new PostBindStrategy(
                httpData -> {
                    try {
                        return Flux.just(new String(httpData.get()));
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                },
                new CustomServiceActionFactory<>());
    }

    @Test
    void build() {
        var routBinder = spy(new PostRoutBinder(UPI, bindStrategy));

        var result = new AbstractServiceTest()
                .prepServerAndMakeResponse("127.0.0.1", TEST_DATA, routBinder.bind());

        assertEquals(TEST_DATA, result);

    }

}