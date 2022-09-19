package ru.kilai.server.routs;

import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public class PostRoutBinder implements ActionRoutBinder {
    private final String uri;
    private final BindStrategy bindStrategy;

    public PostRoutBinder(String uri, BindStrategy bindStrategy) {
        this.uri = uri;
        this.bindStrategy = bindStrategy;
    }

    @Override
    public Consumer<HttpServerRoutes> bind() {
        return httpServerRoutes -> httpServerRoutes.post(uri, bindStrategy);
    }

}
