package ru.kilai.servise.actions;

import reactor.core.publisher.Flux;

public interface ServiceAction<R> {

    Flux<R> execute();
}
