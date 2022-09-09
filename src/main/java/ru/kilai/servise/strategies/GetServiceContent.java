package ru.kilai.servise.strategies;

import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

public class GetServiceContent implements RequestHandler<Flux<String>, String> {
    private final HttpClient client;

    public GetServiceContent(HttpClient client) {
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
