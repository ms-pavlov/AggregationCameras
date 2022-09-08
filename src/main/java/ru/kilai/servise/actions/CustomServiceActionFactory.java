package ru.kilai.servise.actions;

import reactor.core.publisher.Flux;

import java.util.function.Function;

public class CustomServiceActionFactory<T, R> implements ServiceActionFactory<T, R> {
    @Override
    public ServiceAction<R> createAction(Flux<T> input, Function<T, Flux<R>> action) {
        return new CustomServiceAction<>(input, action);
    }
}
