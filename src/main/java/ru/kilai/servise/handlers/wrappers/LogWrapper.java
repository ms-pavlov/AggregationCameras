package ru.kilai.servise.handlers.wrappers;

import reactor.core.publisher.Flux;
import ru.kilai.servise.handlers.RequestHandler;

public class LogWrapper<T, R> implements RequestHandler<T, R> {
    private final RequestHandler<T, R> function;

    public LogWrapper(RequestHandler<T, R> handler) {
        this.function = handler;
    }

    @Override
    public Flux<R> apply(T t) {
        return function.apply(t).log();
    }
}
