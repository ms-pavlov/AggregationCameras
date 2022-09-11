package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;

public class GetParameters implements RequestHandler<String, String> {
    @Override
    public Flux<String> apply(String parameter) {
        return Flux.just(parameter);
    }
}
