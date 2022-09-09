package ru.kilai.servise;

import reactor.core.publisher.Flux;
import ru.kilai.servise.strategies.RequestHandler;

public interface ServiceActionFactory<T, R> {

    ServiceAction<R> createAction(Flux<T> input, RequestHandler<T, R> action);
}
