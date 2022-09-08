package ru.kilai.config.routs;

import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public interface ActionRoutBuilder {

    Consumer<HttpServerRoutes> build();
}