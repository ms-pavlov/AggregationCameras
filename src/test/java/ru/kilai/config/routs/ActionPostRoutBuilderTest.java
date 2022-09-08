package ru.kilai.config.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;
import ru.kilai.config.HttpRNettyApplication;
import ru.kilai.config.HttpResponseTask;

import java.io.IOException;
import java.time.Duration;

class ActionPostRoutBuilderTest {
    private static final Logger log = LoggerFactory.getLogger(ActionPostRoutBuilderTest.class);
    private static final String UPI = "/";
    private static final int PORT = (int) Math.round(1000 + Math.random() * 5000);

    @Test
    void build() {
        var routBuilder = new ActionPostRoutBuilder(UPI, ActionPostRoutBuilderTest::toFlux).build();

        var server = new HttpRNettyApplication(PORT)
                .route(routBuilder)
                .run();

        var response = HttpClient.create()
                .post()
                .uri("127.0.0.1:" + PORT + "/")
                .
                .send(ByteBufFlux.fromString(Mono.just("1")))
                .responseSingle((res, content) -> Mono.just(res.status().code()))
                .block(Duration.ofMillis(200));

        log.debug("response {}", response);

        server.disposeNow();
    }

    private static Flux<String> toFlux(HttpData httpData) {
        try {
            return Flux.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}