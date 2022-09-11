package ru.kilai.server;

import reactor.netty.DisposableChannel;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;
import ru.kilai.server.config.ServerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class AggregationHttpServer implements CustomHttpServer {
    private final List<Consumer<HttpServerRoutes>> conditions;
    private final HttpServer httpServer;

    private DisposableServer disposableServer;


    public AggregationHttpServer(HttpServer httpServer) {
        this.conditions = new ArrayList<>();
        this.httpServer = httpServer;
    }

    public AggregationHttpServer(ServerConfig config) {
        this(config.configure());
    }

    @Override
    public CustomHttpServer route(Consumer<HttpServerRoutes> condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public void start() {
        stop();
        disposableServer = httpServer.route(routes -> conditions.forEach(condition -> condition.accept(routes)))
                .bindNow();
    }

    @Override
    public void stop() {
        Optional.ofNullable(disposableServer)
                .ifPresent(DisposableChannel::disposeNow);
    }
}
