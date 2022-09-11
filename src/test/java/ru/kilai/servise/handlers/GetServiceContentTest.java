package ru.kilai.servise.handlers;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.client.CustomContentHttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.util.AbstractServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetServiceContentTest {
    private static final String TEST_DATA = "same test data";
    private static final int PORT = (int) (1000 + Math.random() * 5000);

    @Test
    void apply() {
        var service = new AggregationHttpServer(new AggregationServerConfig(PORT))
                .route(httpServerRoutes -> httpServerRoutes
                        .get("/",
                                (httpServerRequest, httpServerResponse) ->
                                        httpServerResponse.sendString(Flux.just(TEST_DATA))));
        service.start();

        RequestHandler<HttpData, String> postHandler = new PostParameters();

        System.out.println("127.0.0.1:" + PORT + "/");
        var serviceContent = spy(new ContentHttpRequest(new CustomContentHttpClient()));
        var handler = postHandler.andThen(serviceContent)
                .andThen(byteBufFluxFlux -> byteBufFluxFlux.flatMap(ByteBufFlux::asString));
        var response = new AbstractServiceTest().prepServerAndMakeResponse("127.0.0.1", "127.0.0.1:" + PORT + "/", handler);

        service.stop();

        assertEquals(TEST_DATA, response);
        verify(serviceContent, times(1)).apply(any());


    }
}