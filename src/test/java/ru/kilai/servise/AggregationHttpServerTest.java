package ru.kilai.servise;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.routs.ActionPostRoutBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregationHttpServerTest {
    private static final String UPI = "/";
    private static final String TEST_DATA = "same test data";
    private static final String TEST_DATA_NAME = "name";
    @Test
    void checkStart() {
        var server = new AggregationHttpServer(new AggregationServerConfig());
        assertDoesNotThrow(() -> server.start().disposeNow());
    }

    @Test
    void checkRoute() {
        String host = "127.0.0.2";
        int port = (int) (1000 + Math.random()*5000);

        var routBuilder = new ActionPostRoutBuilder(UPI, this::toFlux).build();
        var dServer = new AggregationHttpServer(new AggregationServerConfig())
                .route(routBuilder)
                .start();

        var response = makeResponse("127.0.0.1:8080/");

        dServer.disposeNow();

        dServer = new AggregationHttpServer(new AggregationServerConfig(host, port, 1))
                .route(routBuilder)
                .start();

        response = makeResponse(host + ":" + port + "/");

        assertEquals(TEST_DATA, response);

        dServer.disposeNow();
    }

    private String makeResponse(String uri) {
        return HttpClient.create()
                .post()
                .uri(uri)
                .sendForm((httpClientRequest, httpClientForm) -> httpClientForm.attr(TEST_DATA_NAME, TEST_DATA))
                .responseContent()
                .aggregate()
                .asString()
                .block();
    }

    private Flux<String> toFlux(HttpData httpData) {
        try {
            return Flux.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}