package ru.kilai.servise.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import ru.kilai.servise.CustomServiceAction;

import java.util.function.Function;

public class ActionLogWrapper implements Function<Object, Flux<String>> {
    private static final Logger log = LoggerFactory.getLogger(CustomServiceAction.class);
    private final Function<Object, Flux<String>> function;

    public ActionLogWrapper(Function<Object, Flux<String>> function) {
        this.function = function;
    }

    @Override
    public Flux<String> apply(Object o) {
        return function.apply(o);
    }

    @Override
    public <V> Function<Object, V> andThen(Function<? super Flux<String>, ? extends V> after) {
        return Function.super.andThen(after);
    }
}
