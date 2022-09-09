package ru.kilai.servise.strategies;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;

import java.io.IOException;

public class GetRequestParameters implements RequestHandler<HttpData, String> {
    @Override
    public Flux<String> apply(HttpData httpData) {
        try {
            return Flux.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
