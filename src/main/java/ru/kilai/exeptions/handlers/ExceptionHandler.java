package ru.kilai.exeptions.handlers;

import reactor.core.publisher.Flux;
import ru.kilai.servise.handlers.RequestHandler;

import java.util.function.BiFunction;

public interface ExceptionHandler<T, R> extends BiFunction<RequestHandler<? super T, R>, T, Flux<R>> {

}
