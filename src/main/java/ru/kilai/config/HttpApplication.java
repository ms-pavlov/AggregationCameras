package ru.kilai.config;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public interface HttpApplication {
    HttpApplication route(Consumer<HttpServerRoutes> condition);

    DisposableServer run();
}
