package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;

public class SchedulerWrapper<T, R> implements RequestHandler<T, R> {

    private final RequestHandler<T, R> handler;
    private final ExecutorService executorService;

    public SchedulerWrapper(RequestHandler<T, R> handler, ExecutorService executorService) {
        this.handler = handler;
        this.executorService = executorService;
    }

    @Override
    public Flux<R> apply(T t) {
        return Flux.just(t)
                .flatMap(handler)
                .publishOn(Schedulers.fromExecutor(executorService));
    }
}
