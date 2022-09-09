package ru.kilai.server.routs;

import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public interface ActionRoutBinder {
    Consumer<HttpServerRoutes> bind();
}
