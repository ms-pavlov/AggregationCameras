package ru.kilai.servise.handlers.factories;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.config.SimplerThreadFactory;
import ru.kilai.servise.handlers.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomServiceHandlerFactory implements ServiceHandlerFactory {
    private static final int DEFAULT_EXECUTOR_POOL_SIZE = 1;
    private final  ExecutorService executorService;

    public CustomServiceHandlerFactory(int executorPoolSize) {
        executorService = Executors.newFixedThreadPool(Math.max(executorPoolSize, DEFAULT_EXECUTOR_POOL_SIZE),
                new SimplerThreadFactory("worker-"));
    }

    public CustomServiceHandlerFactory() {
        this(DEFAULT_EXECUTOR_POOL_SIZE);
    }


    @Override
    public RequestHandler<HttpData, String> createPostParametersHandler() {
        return new LogWrapper<>(new SchedulerWrapper<>(new PostParameters(), executorService));
    }

    @Override
    public RequestHandler<Flux<String>, String> createContentHttpRequestHandler(HttpClient client) {
        return new LogWrapper<>(new SchedulerWrapper<>(new ContentHttpRequest(client), executorService));
    }
}
