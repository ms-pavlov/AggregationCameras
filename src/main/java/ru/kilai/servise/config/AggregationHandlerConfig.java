package ru.kilai.servise.config;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import ru.kilai.client.CustomContentHttpClient;
import ru.kilai.exeptions.CustomExceptionHandlerFactory;
import ru.kilai.exeptions.ExceptionHandlerMapImpl;
import ru.kilai.exeptions.handlers.ExceptionHandler;
import ru.kilai.exeptions.handlers.RetryAggregationHandler;
import ru.kilai.servise.handlers.RequestHandler;
import ru.kilai.servise.handlers.exceptions.BadPostParametersException;
import ru.kilai.servise.handlers.exceptions.CameraInfoJsonException;
import ru.kilai.servise.handlers.exceptions.UrlListParserException;
import ru.kilai.servise.handlers.factories.CustomServiceHandlerFactory;
import ru.kilai.servise.handlers.factories.ServiceHandlerFactory;
import ru.kilai.servise.handlers.wrappers.ExceptionWrapper;

import java.util.List;
import java.util.Map;

public class AggregationHandlerConfig {
    private static final int MIN_HANDLER_POOL = 1;
    private static final int MIN_CLIENT_POOL = 1;
    private static final int MIN_RETRY_DELAY = 0;

    private static final List<Class<?>> aggregationException = List.of(BadPostParametersException.class,
            CameraInfoJsonException.class, UrlListParserException.class);

    private final ServiceHandlerFactory serviceHandlerFactory;
    private final CustomContentHttpClient httpClient;
    private final RequestHandler<Flux<String>, String> retryHandler;

    public AggregationHandlerConfig(int handlerSchedulerPoolSize, int clientEventPoolSize, int retryDelay) {
        serviceHandlerFactory = new CustomServiceHandlerFactory(Math.max(handlerSchedulerPoolSize, MIN_HANDLER_POOL));
        httpClient = new CustomContentHttpClient(Math.max(clientEventPoolSize, MIN_CLIENT_POOL));

        var aggregationHandler = prepAggregationHandler();

        ExceptionHandler<Flux<String>, String> retry = new RetryAggregationHandler(
                prepAggregationHandler(),
                Math.max(retryDelay, MIN_RETRY_DELAY));

        var handlerMap = new ExceptionHandlerMapImpl<Flux<String>, String>();
        aggregationException.forEach(excClass ->
                handlerMap.putHandler(excClass, aggregationHandler.getClass(), retry));

        var handlerFactory = new CustomExceptionHandlerFactory<>(handlerMap);

        retryHandler = new ExceptionWrapper<>(aggregationHandler, handlerFactory);
    }

    public RequestHandler<Flux<String>, String> getRetryHandler() {
        return retryHandler;
    }

    public RequestHandler<HttpData, String> prepPostAggregationHandler() {
        return serviceHandlerFactory.createPostParameters();
    }

    public RequestHandler<Map.Entry<String, List<String>>, String> prepGetAggregationHandler() {
        return serviceHandlerFactory.createGetParameters();
    }

    private RequestHandler<Flux<String>, String> prepAggregationHandler() {
        return serviceHandlerFactory.createContentHttpRequestHandler(httpClient)
                .andThen(serviceHandlerFactory.createUrlListParser())
                .andThen(serviceHandlerFactory.createCameraInfoHttpRequest(httpClient))
                .andThen(serviceHandlerFactory.createCameraInfoToJsonString());
    }
}
