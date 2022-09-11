package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;
import ru.kilai.exeptions.ExceptionHandlerFactory;

public class ExceptionWrapper<T, R> implements RequestHandler<T, R> {
    private final ExceptionHandlerFactory<T, R> handlerFactory;
    private final RequestHandler<T, R> handler;

    public ExceptionWrapper(RequestHandler<T, R> handler,
                            ExceptionHandlerFactory<T, R> handlerFactory) {
        this.handlerFactory = handlerFactory;
        this.handler = handler;
    }

    @Override
    public Flux<R> apply(T t) {
        return handler
                .apply(t)
                .onErrorResume(throwable -> getExceptionHandler(throwable, t));
    }

    private Flux<R> getExceptionHandler(Throwable e, T t) {
        return handlerFactory
                .getHandler(e.getClass(), handler)
                .apply(handler, t);
    }


}
