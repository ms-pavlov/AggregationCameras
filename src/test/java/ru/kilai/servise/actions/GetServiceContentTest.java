package ru.kilai.servise.actions;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetServiceContentTest {
    private static final int PORT = (int) Math.round(1000 + Math.random() * 5000);
    private static final String DATA = "ans";

    @Test
    void apply() {
        var serviceContent = new GetServiceContent(HttpClient.create());

        var server = new AggregationHttpServer(new AggregationServerConfig(PORT))
                .route(httpServerRoutes -> httpServerRoutes.get("/",
                        (httpServerRequest, httpServerResponse) -> httpServerResponse.sendString(Flux.just(DATA))))
                .start();


        var test = serviceContent.apply(Flux.just("127.0.0.1:"+PORT+"/")).blockFirst();

        assertEquals(DATA, test);
        server.disposeNow();

    }
}