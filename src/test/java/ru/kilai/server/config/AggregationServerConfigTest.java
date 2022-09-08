package ru.kilai.server.config;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.routs.ActionPostRoutBuilder;

import java.io.IOException;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregationServerConfigTest {
    private static final Logger log = LoggerFactory.getLogger(AggregationServerConfigTest.class);
    private final static int DEFAULT_PORT = 8080;
    private final static String DEFAULT_HOST = "127.0.0.1";
    private static final String TEST_DATA = "same test data";
    private static final String TEST_DATA_NAME = "name";

    private NavigableSet<String> threads;

    @BeforeEach
    void setUp() {
        threads = new ConcurrentSkipListSet<>();
    }

    @Test
    void checkConstructor() {
        assertEquals(0, threads.size());

        var config = new AggregationServerConfig();

        var server = config.configure()
                .route(new ActionPostRoutBuilder("/", this::toFlux).build())
                .bindNow();

        assertEquals(DEFAULT_PORT, server.port());
        assertEquals(DEFAULT_HOST, server.host());

        server.disposeNow();
    }

    @Test
    void checkConstructorWithPort() {
        int port = (int) (1000 + Math.random()*5000);

        var config = new AggregationServerConfig(port);

        var server = config.configure()
                .route(new ActionPostRoutBuilder("/", this::toFlux).build())
                .bindNow();

        assertEquals(port, server.port());
        assertEquals(DEFAULT_HOST, server.host());

        server.disposeNow();
    }

    @Test
    void checkConstructorWithPortAndEventLoop() {
        int port = (int) (1000 + Math.random()*5000);
        int eventLoop = 10;

        var config = new AggregationServerConfig(port, eventLoop);

        var httpServer = config.configure();
        var server = httpServer
                .route(new ActionPostRoutBuilder("/", this::toFlux).build())
                .bindNow();

        assertEquals(port, server.port());
        assertEquals(DEFAULT_HOST, server.host());

        server.disposeNow();
    }

    @Test
    void checkConstructorWithAll() {
        String host = "127.0.0.2";
        int port = (int) (1000 + Math.random()*5000);
        int eventLoop = 10 ;

        var config = new AggregationServerConfig(host, port, eventLoop);

        var httpServer = config.configure();
        httpServer.warmup().block();
        var server = httpServer
                .route(new ActionPostRoutBuilder("/", this::toFlux).build())
                .bindNow();

        assertEquals(port, server.port());
        assertEquals(host, server.host());

        server.disposeNow();
    }

    private Flux<String> toFlux(HttpData httpData) {
        try {
            threads.add(Thread.currentThread().getName());
            return Flux.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}