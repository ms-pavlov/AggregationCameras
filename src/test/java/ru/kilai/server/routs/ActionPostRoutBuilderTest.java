package ru.kilai.server.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionPostRoutBuilderTest {
    private static final int PORT = (int) Math.round(1000 + Math.random() * 5000);
    private static final String TEST_DATA = "same test data";
    private static final String TEST_DATA_NAME = "name";
    private static final String UPI = "/";


    @Test
    void build() {
        var routBuilder = new ActionPostRoutBuilder(UPI, this::toFlux).build();

        var server = new AggregationHttpServer(new AggregationServerConfig(PORT))
                .route(routBuilder)
                .start();

        var response = HttpClient.create()
                .post()
                .uri("127.0.0.1:" + PORT + "/")
                .sendForm((httpClientRequest, httpClientForm) -> httpClientForm.attr(TEST_DATA_NAME, TEST_DATA))
                .responseContent()
                .aggregate()
                .asString()
                .block();

        assertEquals(TEST_DATA, response);

        server.disposeNow();
    }

    private Flux<String> toFlux(HttpData httpData) {
        try {
            if (httpData.getName().equals(TEST_DATA_NAME)) {
                return Flux.just(new String(httpData.get()));
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}