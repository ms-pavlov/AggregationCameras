package ru.kilai.servise;

import reactor.core.publisher.Flux;
import ru.kilai.servise.handlers.RequestHandler;

public class CustomServiceActionFactory<T, R> implements ServiceActionFactory<T, R> {

    public CustomServiceActionFactory() {
    }

    @Override
    public ServiceAction<R> createAction(Flux<T> input, RequestHandler<T, R> action) {
        return new CustomServiceAction<>(input, action);
    }
}
