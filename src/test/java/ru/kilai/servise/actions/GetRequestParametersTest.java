package ru.kilai.servise.actions;

import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.routs.ActionPostRoutBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetRequestParametersTest {
    private static final int PORT = (int) Math.round(1000 + Math.random() * 5000);
    private static final String UPI = "/";
    private static final String TEST_DATA = "same test data";
    private static final String TEST_DATA_NAME = "name";

    @Test
    void apply() {
        var parameters = new GetRequestParameters();

        var routBuilder = new ActionPostRoutBuilder(UPI, parameters).build();

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