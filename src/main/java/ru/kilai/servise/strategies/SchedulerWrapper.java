package ru.kilai.servise.strategies;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;

public class SchedulerWrapper<T, R> implements RequestHandler<T, R> {

    private final RequestHandler<T, R> function;
    private final ExecutorService executorService;

    public SchedulerWrapper(RequestHandler<T, R> function, ExecutorService executorService) {
        this.function = function;
        this.executorService = executorService;
    }

    @Override
    public Flux<R> apply(T t) {
        return Flux.just(t)
                .flatMap(function)
                .publishOn(Schedulers.fromExecutor(executorService));
    }
}
