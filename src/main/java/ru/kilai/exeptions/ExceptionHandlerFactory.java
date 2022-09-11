package ru.kilai.exeptions;

import ru.kilai.exeptions.handlers.ExceptionHandler;

public interface ExceptionHandlerFactory<T, R> {

    ExceptionHandler<T, R> getHandler(Class<?> exception, Object owner);
}
