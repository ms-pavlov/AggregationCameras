package ru.kilai.servise.strategies;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
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
                                        httpServerResponse.sendString(Flux.just(TEST_DATA))))
                .start();

        RequestHandler<HttpData, String> handler = new GetRequestParameters();

        System.out.println("127.0.0.1:"+PORT+"/");
        var serviceContent = spy(new GetServiceContent(HttpClient.create()));
        var response = new AbstractServiceTest().prepServerAndMakeResponse("127.0.0.1", "127.0.0.1:"+PORT+"/", handler.andThen(serviceContent));

        service.disposeNow();

        assertEquals(TEST_DATA, response);
        verify(serviceContent, times(1)).apply(any());


    }
}