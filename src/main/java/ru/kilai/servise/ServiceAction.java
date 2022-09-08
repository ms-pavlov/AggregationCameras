package ru.kilai.servise;

import reactor.core.publisher.Flux;

import java.util.function.Function;

public interface ServiceAction<R> {

    Flux<R> execute();

    <V> ServiceAction<V> andThen(Function<R, Flux<V>> after);
}
