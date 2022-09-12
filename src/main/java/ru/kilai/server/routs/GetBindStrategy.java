package ru.kilai.server.routs;

import io.netty.handler.codec.http.QueryStringDecoder;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.servise.ServiceActionFactory;
import ru.kilai.servise.handlers.RequestHandler;

import java.util.List;
import java.util.Map;

public class GetBindStrategy implements BindStrategy {
    private final RequestHandler<Map.Entry<String, List<String>>, String> handler;
    private final ServiceActionFactory<Map.Entry<String, List<String>>, String> actionFactory;


    public GetBindStrategy(RequestHandler<Map.Entry<String, List<String>>, String> handler,
                           ServiceActionFactory<Map.Entry<String, List<String>>, String>  actionFactory) {
        this.handler = handler;
        this.actionFactory = actionFactory;
    }

    @Override
    public Publisher<Void> apply(HttpServerRequest req, HttpServerResponse res) {
        var parameters = new QueryStringDecoder(req.uri()).parameters();
        return res
                .sendString(actionFactory
                        .createAction(Flux.fromStream(parameters.entrySet().stream()), handler)
                        .execute());
    }
}
