package ru.kilai.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import ru.kilai.servise.strategies.RequestHandler;

public class CustomServiceAction<T, R> implements ServiceAction<R> {
    private static final Logger log = LoggerFactory.getLogger(CustomServiceAction.class);
    private final Flux<T> input;
    private final RequestHandler<T, R> action;

    public CustomServiceAction(Flux<T> input, RequestHandler<T, R> action) {
        this.input = input;
        this.action = action;
    }

    @Override
    public Flux<R> execute() {
        log.debug(Thread.currentThread().getName());
        return input.flatMap(action);
    }
}
