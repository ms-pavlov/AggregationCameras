package ru.kilai.config;

import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HttpRNettyApplication implements HttpApplication{

    private final List<Consumer<HttpServerRoutes>> conditions;
    private final HttpServer httpServer;

    public HttpRNettyApplication() {
        this.conditions = new ArrayList<>();
        httpServer = HttpServer.create().port(8080);
        httpServer.warmup().block();
    }

    @Override
    public HttpApplication route(Consumer<HttpServerRoutes> condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public void run() {
        httpServer.route(routes -> conditions.forEach(condition -> condition.accept(routes)))
                .bindNow().onDispose().block();
    }
}
