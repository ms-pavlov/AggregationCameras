package ru.kilai.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class CustomServiceAction<T, R> implements ServiceAction<R> {
    private static final Logger log = LoggerFactory.getLogger(CustomServiceAction.class);
    private final Flux<T> input;
    private final Function<T, Flux<R>> action;

    public CustomServiceAction(Flux<T> input, Function<T, Flux<R>> action) {
        this.input = input;
        this.action = action;
    }

    @Override
    public Flux<R> execute() {
        return input.flatMap(t -> {
            log.debug("execute item: {}", t);
            return action.apply(t);
        });
    }

    @Override
    public <V> ServiceAction<V> andThen(Function<R, Flux<V>> after) {
        return new CustomServiceAction<>(input, action
                .andThen(rFlux -> rFlux.flatMap(r -> {
                    log.debug("andThen item: {}", r);
                    return after.apply(r);
                })));
    }
}
