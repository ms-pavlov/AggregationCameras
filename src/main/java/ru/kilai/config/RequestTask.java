package ru.kilai.config;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Mono;
import reactor.netty.NettyOutbound;

import java.util.function.Function;

public interface RequestTask {

    NettyOutbound execute(Function<HttpData, Mono<String>> function);
}
