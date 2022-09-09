package ru.kilai.server.routs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public class PostRoutBinder implements ActionRoutBinder {
    private static final Logger log = LoggerFactory.getLogger(PostRoutBinder.class);

    private final String uri;
    private final BindStrategy bindStrategy;

    public PostRoutBinder(String uri, BindStrategy bindStrategy) {
        this.uri = uri;
        this.bindStrategy = bindStrategy;
    }


    @Override
    public Consumer<HttpServerRoutes> bind() {
        log.debug("uri: {}", uri);
        return httpServerRoutes -> httpServerRoutes.post(uri, bindStrategy);
    }

}
