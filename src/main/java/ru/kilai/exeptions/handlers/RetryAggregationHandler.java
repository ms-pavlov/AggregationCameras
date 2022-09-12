package ru.kilai.exeptions.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import ru.kilai.exeptions.BedRequestException;
import ru.kilai.exeptions.CustomExceptionHandlerFactory;
import ru.kilai.servise.handlers.RequestHandler;

import java.time.Duration;

public class RetryAggregationHandler implements ExceptionHandler<Flux<String>, String> {
    private static final Logger log = LoggerFactory.getLogger(RetryAggregationHandler.class);
    private final RequestHandler<Flux<String>, String> handler;
    private final int delay;

    public RetryAggregationHandler(RequestHandler<Flux<String>, String> handler, int delay) {
        this.handler = handler;
        this.delay = delay;
    }

    @Override
    public Flux<String> apply(RequestHandler<? super Flux<String>, String> stringRequestHandler, Flux<String> flux) {
        return handler
                .apply(flux
                        .delayElements(Duration.ofMillis(delay)))
                .onErrorMap(BedRequestException::new);
    }
}
