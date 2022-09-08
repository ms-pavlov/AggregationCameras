package ru.kilai.servise.actions;

import reactor.core.publisher.Flux;

import java.util.function.Function;

public class CustomServiceAction<T, R> implements ServiceAction<R>{
    private final Flux<T> input;
    private final Function<T, Flux<R>> action;

    protected CustomServiceAction(Flux<T> input, Function<T, Flux<R>> action) {
        this.input = input;
        this.action = action;
    }

    @Override
    public Flux<R> execute() {
        return input.flatMap(action);
    }
}
