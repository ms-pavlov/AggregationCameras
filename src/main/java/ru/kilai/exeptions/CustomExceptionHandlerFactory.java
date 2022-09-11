package ru.kilai.exeptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kilai.exeptions.handlers.ExceptionHandler;

import java.util.Optional;

public class CustomExceptionHandlerFactory<T, R> implements ExceptionHandlerFactory<T, R> {
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandlerFactory.class);
    private final ExceptionHandlerMap<T, R> handlerMap;

    public CustomExceptionHandlerFactory(ExceptionHandlerMap<T, R> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public ExceptionHandler<T, R> getHandler(Class<?> exception, Object owner) {
        return Optional.ofNullable(handlerMap)
                .map(exceptionHandlerMap -> exceptionHandlerMap.getHandler(exception, owner.getClass()))
                .map(trExceptionHandler -> {
                    log.debug("find solution {}", trExceptionHandler);
                    return trExceptionHandler;
                })
                .orElseThrow(BedRequestException::new);
    }
}
