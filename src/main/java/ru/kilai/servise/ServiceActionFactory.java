package ru.kilai.servise;

import reactor.core.publisher.Flux;

import java.util.function.Function;

public interface ServiceActionFactory<T, R> {

    ServiceAction<R> createAction(Flux<T> input, Function<T, Flux<R>> action);
}
