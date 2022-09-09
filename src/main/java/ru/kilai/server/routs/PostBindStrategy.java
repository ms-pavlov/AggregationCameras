package ru.kilai.server.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.servise.ServiceActionFactory;
import ru.kilai.servise.strategies.RequestHandler;

public class PostBindStrategy implements BindStrategy {
    private final RequestHandler<HttpData, String> handler;
    private final ServiceActionFactory<HttpData, String> actionFactory;

    public PostBindStrategy(RequestHandler<HttpData, String> handler, ServiceActionFactory<HttpData, String> actionFactory) {
        this.handler = handler;
        this.actionFactory = actionFactory;
    }

    @Override
    public Publisher<Void> apply(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) {
        return httpServerResponse
                .sendString(actionFactory
                        .createAction(httpServerRequest.receiveForm(), handler)
                        .execute());
    }
}
