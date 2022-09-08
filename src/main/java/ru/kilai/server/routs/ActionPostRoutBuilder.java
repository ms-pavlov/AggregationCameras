package ru.kilai.server.routs;

import io.netty.handler.codec.http.multipart.HttpData;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import reactor.netty.http.server.HttpServerRoutes;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.ServiceActionFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class ActionPostRoutBuilder implements ActionRoutBuilder {
    private static final Logger log = LoggerFactory.getLogger(ActionPostRoutBuilder.class);

    private final String uri;
    private final ServiceActionFactory<HttpData, String> actionFactory;
    private final Function<HttpData, Flux<String>> function;

    public ActionPostRoutBuilder(String uri, Function<HttpData, Flux<String>> function) {
        this.uri = uri;
        this.function = function;
        this.actionFactory = new CustomServiceActionFactory<>();
    }

    @Override
    public Consumer<HttpServerRoutes> build() {
        log.debug("uri: {}", uri);
        return httpServerRoutes -> httpServerRoutes.post(uri, this::apply);
    }

    private Publisher<Void> apply(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) {
        log.debug("request from: {}, uri: {}", httpServerRequest.remoteAddress(), httpServerRequest.uri());
        return httpServerResponse.sendString(
                actionFactory
                        .createAction(httpServerRequest.receiveForm(), function)
                        .execute()
        );
    }
}
