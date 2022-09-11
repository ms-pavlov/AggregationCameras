package ru.kilai.server.routs;

import org.junit.jupiter.api.Test;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.handlers.GetParameters;
import ru.kilai.util.AbstractServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetBindStrategyTest {
    private static final String PING = "ping";

    @Test
    void apply() {
        var handler = spy(new GetParameters());
        var actionFactory = new CustomServiceActionFactory<String, String>();
        var bindStrategy = spy(new GetBindStrategy(handler, actionFactory, PING));

        var result = new AbstractServiceTest().prepServerAndMakeGetResponse("127.0.0.1", bindStrategy);

        assertEquals(PING, result);
        verify(handler, times(1)).apply(any());
        verify(bindStrategy, times(1)).apply(any(), any());
    }
}