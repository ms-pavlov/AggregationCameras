package ru.kilai.server;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;
import ru.kilai.server.config.ServerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AggregationHttpServer implements CustomHttpServer{
    private final List<Consumer<HttpServerRoutes>> conditions;
    private final HttpServer httpServer;

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
    public DisposableServer start() {
        return httpServer.route(routes -> conditions.forEach(condition -> condition.accept(routes)))
                .bindNow();
    }
}
