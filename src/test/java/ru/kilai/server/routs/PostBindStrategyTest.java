package ru.kilai.server.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.handlers.PostParameters;
import ru.kilai.util.AbstractServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostBindStrategyTest {
    private static final String PING = "ping";
    @Test
    void apply() {
        var handler = spy(new PostParameters());
        var actionFactory = new CustomServiceActionFactory<HttpData, String>();
        var bindStrategy = spy(new PostBindStrategy(handler, actionFactory));

        var result = new AbstractServiceTest().prepServerAndMakeResponse("127.0.0.1", PING, bindStrategy);

        assertEquals(PING, result);
        verify(handler, times(1)).apply(any());
        verify(bindStrategy, times(1)).apply(any(), any());
    }
}