package ru.kilai.server.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.reactivestreams.Publisher;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.servise.ServiceActionFactory;
import ru.kilai.servise.strategies.RequestHandler;

import java.util.concurrent.ExecutorService;

public class BindStrategyWithScheduler implements BindStrategy {
    private final RequestHandler<HttpData, String> handler;
    private final ServiceActionFactory<HttpData, String> actionFactory;
    private final ExecutorService executorService;

    public BindStrategyWithScheduler(RequestHandler<HttpData, String> handler,
                                     ServiceActionFactory<HttpData, String> actionFactory,
                                     ExecutorService executorService) {
        this.handler = handler;
        this.actionFactory = actionFactory;
        this.executorService = executorService;
    }

    @Override
    public Publisher<Void> apply(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) {
        return httpServerResponse
                .sendString(actionFactory
                        .createAction(httpServerRequest.receiveForm(), handler)
                        .execute()
                        .publishOn(Schedulers.fromExecutor(executorService)));
    }
}
