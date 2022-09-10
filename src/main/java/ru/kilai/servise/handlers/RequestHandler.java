package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.function.Function;

public interface RequestHandler<T, R> extends Function<T, Flux<R>> {

    default <V> RequestHandler<T, V> andThen(RequestHandler<Flux<R>, V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }
}
