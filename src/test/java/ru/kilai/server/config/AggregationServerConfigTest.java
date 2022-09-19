package ru.kilai.server.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import ru.kilai.server.routs.PostBindStrategy;
import ru.kilai.server.routs.PostRoutBinder;
import ru.kilai.servise.CustomServiceActionFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregationServerConfigTest {
    private final static int DEFAULT_PORT = 8080;
    private final static String DEFAULT_HOST = "127.0.0.1";
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
    void checkConstructor() {

        var config = new AggregationServerConfig();

        var server = config.configure()
                .route(new PostRoutBinder("/", bindStrategy).bind())
                .bindNow();

        assertEquals(DEFAULT_PORT, server.port());
        assertEquals(DEFAULT_HOST, server.host());

        server.disposeNow();
    }

    @Test
    void checkConstructorWithPort() {
        int port = (int) (1000 + Math.random() * 5000);

        var config = new AggregationServerConfig(port);

        var server = config.configure()
                .route(new PostRoutBinder("/", bindStrategy).bind())
                .bindNow();

        assertEquals(port, server.port());
        assertEquals(DEFAULT_HOST, server.host());

        server.disposeNow();
    }

    @Test
    void checkConstructorWithPortAndEventLoop() {
        int port = (int) (1000 + Math.random() * 5000);
        int eventLoop = 10;

        var config = new AggregationServerConfig(port, eventLoop);

        var httpServer = config.configure();
        var server = httpServer
                .route(new PostRoutBinder("/", bindStrategy).bind())
                .bindNow();

        assertEquals(port, server.port());
        assertEquals(DEFAULT_HOST, server.host());

        server.disposeNow();
    }

    @Test
    void checkConstructorWithAll() {
        String host = "127.0.0.2";
        int port = (int) (1000 + Math.random() * 5000);
        int eventLoop = 10;

        var config = new AggregationServerConfig(host, port, eventLoop);

        var httpServer = config.configure();
        httpServer.warmup().block();
        var server = httpServer
                .route(new PostRoutBinder("/", bindStrategy).bind())
                .bindNow();

        assertEquals(port, server.port());
        assertEquals(host, server.host());

        server.disposeNow();
    }

}