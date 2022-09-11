package ru.kilai.servise.handlers;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import ru.kilai.servise.handlers.exceptions.BadPostParametersException;

import java.io.IOException;

public class PostParameters implements RequestHandler<HttpData, String> {
    @Override
    public Flux<String> apply(HttpData httpData) {
        try {
            return Flux.just(new String(httpData.get()));
        } catch (IOException e) {
            throw new BadPostParametersException(e);
        }
    }
}
