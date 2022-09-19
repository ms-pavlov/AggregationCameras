package ru.kilai.servise.handlers;

import io.netty.handler.codec.http.multipart.HttpData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import ru.kilai.servise.handlers.exceptions.BadPostParametersException;

import java.io.IOException;

public class PostParameters implements RequestHandler<HttpData, String> {

    @Override
    public Flux<String> apply(HttpData httpData) {
        try {
            if (httpData.getName().equals("url")) {
                return Flux.just(new String(httpData.get()));
            }
            return Flux.empty();
        } catch (IOException e) {
            throw new BadPostParametersException(e);
        }
    }
}
