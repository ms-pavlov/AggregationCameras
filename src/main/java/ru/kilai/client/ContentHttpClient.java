package ru.kilai.client;

import reactor.netty.ByteBufFlux;

public interface ContentHttpClient {

    ByteBufFlux get(String uri);
}
