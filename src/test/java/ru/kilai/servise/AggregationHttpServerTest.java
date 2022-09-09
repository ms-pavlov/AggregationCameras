package ru.kilai.servise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.routs.PostBindStrategy;
import ru.kilai.server.routs.PostRoutBinder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregationHttpServerTest {
    private static final String UPI = "/";
    private static final String TEST_DATA = "same test data";
    private static final String TEST_DATA_NAME = "name";
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
    void checkStart() {
        var server = new AggregationHttpServer(new AggregationServerConfig());
        assertDoesNotThrow(() -> server.start().disposeNow());
    }

    @Test
    void checkRoute() {
        String host = "127.0.0.2";
        int port = (int) (1000 + Math.random()*5000);

        var routBuilder = new PostRoutBinder(UPI, bindStrategy).bind();
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

}