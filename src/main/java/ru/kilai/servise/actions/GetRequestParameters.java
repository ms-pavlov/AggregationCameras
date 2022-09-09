package ru.kilai.servise.actions;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.io.IOException;
import java.util.function.Function;

public class GetRequestParameters implements Function<HttpData, Flux<String>> {
    @Override
    public Flux<String> apply(HttpData httpData) {
        try {
            return Flux.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
