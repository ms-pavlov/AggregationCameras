package ru.kilai.server.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.NettyOutbound;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.config.SimplerThreadFactory;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.config.ServerConfig;
import ru.kilai.servise.CustomServiceAction;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.strategies.GetRequestParameters;
import ru.kilai.servise.strategies.RequestHandler;
import ru.kilai.util.AbstractServiceTest;

import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BindStrategyWithSchedulerTest {

    private static final String PING = "ping";

    @Test
    void apply() {
        var handler = spy(new GetRequestParameters());
        var actionFactory = new CustomServiceActionFactory<HttpData, String>();
        var executorService = Executors.newFixedThreadPool(4,
                new SimplerThreadFactory("worker-"));

        var bindStrategy = spy(new BindStrategyWithScheduler(handler, actionFactory, executorService));

        var result = new AbstractServiceTest()
                .prepServerAndMakeResponse("127.0.0.1", PING, bindStrategy);

        assertEquals(PING, result);
        verify(handler, times(1)).apply(any());
        verify(bindStrategy, times(1)).apply(any(), any());
    }

}