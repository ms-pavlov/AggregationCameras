package ru.kilai.servise;

import reactor.core.publisher.Flux;

public interface ServiceAction<R> {

    Flux<R> execute();

}
