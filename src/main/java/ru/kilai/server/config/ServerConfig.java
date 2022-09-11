package ru.kilai.server.config;

import reactor.netty.http.server.HttpServer;

public interface ServerConfig {

    HttpServer configure();
}
