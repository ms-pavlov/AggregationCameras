package ru.kilai.server.routs;

import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public class GetRoutBinder implements ActionRoutBinder {
    private final String uri;
    private final BindStrategy bindStrategy;

    public GetRoutBinder(String uri, BindStrategy bindStrategy) {
        this.uri = uri;
        this.bindStrategy = bindStrategy;
    }

    @Override
    public Consumer<HttpServerRoutes> bind() {
        return httpServerRoutes -> httpServerRoutes.get(uri, bindStrategy);
    }
}
