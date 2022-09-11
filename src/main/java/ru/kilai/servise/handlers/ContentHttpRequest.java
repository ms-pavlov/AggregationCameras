package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.client.ContentHttpClient;

public class ContentHttpRequest implements RequestHandler<Flux<String>, ByteBufFlux> {
    private final ContentHttpClient client;

    public ContentHttpRequest(ContentHttpClient client) {
        this.client = client;
    }

    @Override
    public Flux<ByteBufFlux> apply(Flux<String> input) {
        return input.map(client::get);
    }
}
