package ru.kilai.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.kilai.server.routs.ActionPostRoutBuilder;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public class CustomServiceActionFactory<T, R> implements ServiceActionFactory<T, R> {
    private static final Logger log = LoggerFactory.getLogger(CustomServiceActionFactory.class);

    public CustomServiceActionFactory() {
    }

    @Override
    public ServiceAction<R> createAction(Flux<T> input, Function<T, Flux<R>> action) {
        return new CustomServiceAction<>(input, action);
    }
}
