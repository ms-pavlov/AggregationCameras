package ru.kilai.server.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.servise.CustomServiceAction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostBindStrategyTest {
    private static final String PING = "ping";
    @Test
    void apply() {

        var req = mock(HttpServerRequest.class);
        var res = mock(HttpServerResponse.class);
        var data = mock(HttpData.class);

        when(data.getName()).thenReturn(PING);
        when(req.receiveForm()).thenReturn(Flux.just(data));

        new PostBindStrategy(httpData -> {
            assertEquals(PING, httpData.getName());
            return Flux.just(httpData.getName());
        }, CustomServiceAction::new).apply(req, res);

    }
}