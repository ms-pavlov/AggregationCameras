package ru.kilai.servise.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.client.ContentHttpClient;

public class ContentHttpRequest implements RequestHandler<Flux<String>, ByteBufFlux> {
    private final Logger log = LoggerFactory.getLogger(ContentHttpRequest.class);
    private final ContentHttpClient client;

    public ContentHttpRequest(ContentHttpClient client) {
        this.client = client;
    }

    @Override
    public Flux<ByteBufFlux> apply(Flux<String> input) {

        return input.map(s -> {
            log.debug("input arguments {}", s);
            return s;
        }).map(client::get);
    }
}
