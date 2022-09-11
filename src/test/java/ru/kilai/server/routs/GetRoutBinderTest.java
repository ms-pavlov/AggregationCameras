package ru.kilai.server.routs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.util.AbstractServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class GetRoutBinderTest {
    private static final String TEST_DATA = "same test data";
    private static final String UPI = "/";

    private GetBindStrategy bindStrategy;

    @BeforeEach
    void setUp() {
        this.bindStrategy = new GetBindStrategy(
                Flux::just,
                new CustomServiceActionFactory<>()
                , TEST_DATA);
    }

    @Test
    void build() {
        var routBinder = spy(new GetRoutBinder(UPI, bindStrategy));

        var result = new AbstractServiceTest()
                .prepServerAndMakeGetResponse("127.0.0.1", routBinder.bind());

        assertEquals(TEST_DATA, result);

    }
}