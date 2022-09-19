package ru.kilai.servise.handlers.factories;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.client.ContentHttpClient;
import ru.kilai.config.SimplerThreadFactory;
import ru.kilai.servise.handlers.*;
import ru.kilai.servise.handlers.wrappers.LogWrapper;
import ru.kilai.servise.handlers.wrappers.SchedulerWrapper;

import javax.json.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomServiceHandlerFactory implements ServiceHandlerFactory {
    private static final int MIN_EXECUTOR_POOL_SIZE = 1;
    private final ExecutorService executorService;

    public CustomServiceHandlerFactory(int executorPoolSize) {
        executorService = Executors.newFixedThreadPool(Math.max(executorPoolSize, MIN_EXECUTOR_POOL_SIZE),
                new SimplerThreadFactory("worker-"));
    }

    public CustomServiceHandlerFactory() {
        this(MIN_EXECUTOR_POOL_SIZE);
    }

    @Override
    public RequestHandler<Map.Entry<String, List<String>>, String> createGetParameters() {
        return new LogWrapper<>(new SchedulerWrapper<>(new GetParameters(), executorService));
    }

    @Override
    public RequestHandler<HttpData, String> createPostParameters() {
        return new LogWrapper<>(new SchedulerWrapper<>(new PostParameters(), executorService));
    }

    @Override
    public RequestHandler<Flux<ByteBufFlux>, JsonObject> createUrlListParser() {
        return new LogWrapper<>(new SchedulerWrapper<>(new UrlListParser(), executorService));
    }

    @Override
    public RequestHandler<Flux<JsonObject>, String> createCameraInfoToJsonString() {
        return new LogWrapper<>(new SchedulerWrapper<>(new CameraInfoToJsonString(), executorService));
    }

    @Override
    public RequestHandler<Flux<String>, ByteBufFlux> createContentHttpRequestHandler(ContentHttpClient client) {
        return new LogWrapper<>(new SchedulerWrapper<>(new ContentHttpRequest(client), executorService));
    }

    @Override
    public RequestHandler<Flux<JsonObject>, JsonObject> createCameraInfoHttpRequest(ContentHttpClient client) {
        return new LogWrapper<>(new SchedulerWrapper<>(new CameraInfoHttpRequest(client), executorService));
    }
}
