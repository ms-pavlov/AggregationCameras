package ru.kilai.server.routs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.servise.CustomServiceActionFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionPostRoutBuilderTest {
    private static final int PORT = (int) Math.round(1000 + Math.random() * 5000);
    private static final String TEST_DATA = "same test data";
    private static final String TEST_DATA_NAME = "name";
    private static final String UPI = "/";

    private PostBindStrategy bindStrategy;

    @BeforeEach
    void setUp() {
        this.bindStrategy = new PostBindStrategy(
                httpData -> {
                    try {
                        return Flux.just(new String(httpData.get()));
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                },
                new CustomServiceActionFactory<>());
    }
    @Test
    void build() {
        var routBuilder = new PostRoutBinder(UPI, bindStrategy).bind();

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

}