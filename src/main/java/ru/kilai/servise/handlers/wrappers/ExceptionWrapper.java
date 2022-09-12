package ru.kilai.servise.handlers.wrappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import ru.kilai.exeptions.ExceptionHandlerFactory;
import ru.kilai.servise.handlers.RequestHandler;

public class ExceptionWrapper<T, R> implements RequestHandler<T, R> {
    private final Logger log = LoggerFactory.getLogger(ExceptionWrapper.class);
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
        log.debug("Fail with arguments {}", t);
        return handlerFactory
                .getHandler(e.getClass(), handler)
                .apply(handler, t);
    }


}
