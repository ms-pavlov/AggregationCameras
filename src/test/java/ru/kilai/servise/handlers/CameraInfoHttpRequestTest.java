package ru.kilai.servise.handlers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import ru.kilai.client.ContentHttpClient;
import ru.kilai.client.CustomContentHttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.servise.handlers.exceptions.CameraInfoJsonException;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.jupiter.api.Assertions.*;

class CameraInfoHttpRequestTest {
    private static final Logger log = LoggerFactory.getLogger(CameraInfoHttpRequestTest.class);
    private static final String TEST_DATA1 = "{ \"test\": \"any\" }";
    private static final String TEST_DATA2 = "{ \"newTest\": \"any\" }";
    private static final String BAD_DATA = "{";
    private static final int PORT = (int) (1000 + Math.random() * 5000);

    private final ContentHttpClient client = new CustomContentHttpClient(2);
    private final RequestHandler<Flux<JsonObject>, JsonObject> handler = new CameraInfoHttpRequest(client);


    @Test
    void apply() {
        var service = new AggregationHttpServer(new AggregationServerConfig(PORT))
                .route(httpServerRoutes -> httpServerRoutes
                        .get("/1/",
                                (httpServerRequest, httpServerResponse) ->
                                        httpServerResponse.sendString(Flux.just(TEST_DATA1))))
                .route(httpServerRoutes -> httpServerRoutes
                        .get("/2/",
                                (httpServerRequest, httpServerResponse) ->
                                        httpServerResponse.sendString(Flux.just(TEST_DATA2))));

        service.start();

        var inputJson = Json.createObjectBuilder()
                .add("id", 1)
                .add("sourceDataUrl", "127.0.0.1:" + PORT + "/1/")
                .add("tokenDataUrl", "127.0.0.1:" + PORT + "/2/")
                .build();

        var result = handler.apply(Flux.just(inputJson)).collectList().block();

        log.debug("result {}", result);

        assertNotNull(result);
        assertEquals("any", result.get(0).getString("test"));
        assertEquals("any", result.get(0).getString("newTest"));

        service.stop();
    }

    @Test
    void checkException() {
        var service = new AggregationHttpServer(new AggregationServerConfig(PORT))
                .route(httpServerRoutes -> httpServerRoutes
                        .get("/",
                                (httpServerRequest, httpServerResponse) ->
                                        httpServerResponse.sendString(Flux.just(BAD_DATA))));
        service.start();

        var inputJson = Json.createObjectBuilder()
                .add("id", 1)
                .add("sourceDataUrl", "127.0.0.1:" + PORT + "/")
                .add("tokenDataUrl", "127.0.0.1:" + PORT + "/")
                .build();

        assertThrows(CameraInfoJsonException.class, () -> handler.apply(Flux.just(inputJson)).collectList().block());

        service.stop();
    }
}