package ru.kilai.servise.actions;

import reactor.core.publisher.Flux;

import java.util.function.Function;

public class SchedulerGetRequestWrapper<R> implements Function<Flux<R>, Flux<R>> {

    private final Function<?, Flux<R>> function;


    public SchedulerGetRequestWrapper(Function<?, Flux<R>> function) {
        this.function = function;
    }

    @Override
    public Flux<R> apply(Flux<R> rFlux) {
        return null;
    }
}
