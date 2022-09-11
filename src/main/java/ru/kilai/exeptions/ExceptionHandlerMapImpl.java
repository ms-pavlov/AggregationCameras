package ru.kilai.exeptions;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import ru.kilai.exeptions.handlers.ExceptionHandler;

public class ExceptionHandlerMapImpl<T, R> implements ExceptionHandlerMap<T, R> {

    private final Table<Class<?>, Class<?>, ExceptionHandler<T, R>> handlerTable;

    public ExceptionHandlerMapImpl() {
        handlerTable = HashBasedTable.create();
    }

    @Override
    public void putHandler(Class<?> exception, Class<?> object, ExceptionHandler<T, R> handler) {
        handlerTable.put(exception, object, handler);
    }

    @Override
    public ExceptionHandler<T, R> getHandler(Class<?> exception, Class<?> object) {
        return handlerTable.get(exception, object);
    }
}
