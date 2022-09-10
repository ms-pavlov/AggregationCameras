package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;

public class LogWrapper<T, R> implements RequestHandler<T, R>  {
    private final RequestHandler<T, R> function;

    public LogWrapper(RequestHandler<T, R> function) {
        this.function = function;
    }

    @Override
    public Flux<R> apply(T t) {
        return function.apply(t).log();
    }
}
