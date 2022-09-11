package ru.kilai.server.routs;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.servise.ServiceActionFactory;
import ru.kilai.servise.handlers.RequestHandler;

public class GetBindStrategy implements BindStrategy {
    private final RequestHandler<String, String> handler;
    private final ServiceActionFactory<String, String> actionFactory;
    private final String sourceUrl;

    public GetBindStrategy(RequestHandler<String, String> handler,
                           ServiceActionFactory<String, String> actionFactory,
                           String sourceUrl) {
        this.handler = handler;
        this.actionFactory = actionFactory;
        this.sourceUrl = sourceUrl;
    }

    @Override
    public Publisher<Void> apply(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) {
        return httpServerResponse
                .sendString(actionFactory
                        .createAction(Flux.just(sourceUrl), handler)
                        .execute());
    }
}
