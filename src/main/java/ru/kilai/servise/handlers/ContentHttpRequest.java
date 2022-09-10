package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

public class ContentHttpRequest implements RequestHandler<Flux<String>, String> {
    private final HttpClient client;

    public ContentHttpRequest(HttpClient client) {
        this.client = client;
    }

    @Override
    public Flux<String> apply(Flux<String> input) {
        return input.flatMap(s -> client.get()
            .uri(s)
            .responseContent()
            .asString());
    }
}
