package ru.kilai.server.routs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.handlers.GetParameters;
import ru.kilai.util.AbstractServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class GetRoutBinderTest {
    private static final String TEST_DATA = "same_test_data";
    private static final String UPI = "/";

    private GetBindStrategy bindStrategy;

    @BeforeEach
    void setUp() {
        this.bindStrategy = new GetBindStrategy(
                new GetParameters(),
                new CustomServiceActionFactory<>());
    }

    @Test
    void build() {
        var routBinder = spy(new GetRoutBinder(UPI, bindStrategy));

        var result = new AbstractServiceTest()
                .prepServerAndMakeGetResponse("127.0.0.1", routBinder.bind(), "?url=" + TEST_DATA);

        assertEquals(TEST_DATA, result);

    }
}