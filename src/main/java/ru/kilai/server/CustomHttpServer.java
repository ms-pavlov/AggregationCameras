package ru.kilai.server;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public interface CustomHttpServer {
    CustomHttpServer route(Consumer<HttpServerRoutes> condition);

    DisposableServer start();
}
