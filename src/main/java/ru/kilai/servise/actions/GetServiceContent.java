package ru.kilai.servise.actions;

import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

import java.util.function.Function;

public class GetServiceContent implements Function<Flux<String>, Flux<String>> {
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
