package ru.kilai.exeptions;

import ru.kilai.exeptions.handlers.ExceptionHandler;

public interface ExceptionHandlerMap<T, R> {

    void putHandler(Class<?> exception, Class<?> object, ExceptionHandler<T, R> handler);

    ExceptionHandler<T, R> getHandler(Class<?> exception, Class<?> object);
}
