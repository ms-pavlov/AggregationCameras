package ru.kilai.server.routs;

import io.netty.handler.codec.http.QueryStringDecoder;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.servise.ServiceActionFactory;
import ru.kilai.servise.handlers.RequestHandler;

public class GetBindStrategy implements BindStrategy {
    private final RequestHandler<String, String> handler;
    private final ServiceActionFactory<String, String> actionFactory;


    public GetBindStrategy(RequestHandler<String, String> handler,
                           ServiceActionFactory<String, String> actionFactory) {
        this.handler = handler;
        this.actionFactory = actionFactory;
    }

    @Override
    public Publisher<Void> apply(HttpServerRequest req, HttpServerResponse res) {
        var parameters = new QueryStringDecoder(req.uri()).parameters();
        return res
                .sendString(actionFactory
                        .createAction(Flux.fromStream(parameters.get("url").stream()), handler)
                        .execute());
    }
}
