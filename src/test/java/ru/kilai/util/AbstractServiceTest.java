package ru.kilai.util;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.netty.DisposableServer;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServerRoutes;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.routs.BindStrategy;
import ru.kilai.server.routs.PostBindStrategy;
import ru.kilai.server.routs.PostRoutBinder;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.servise.strategies.RequestHandler;

import java.io.IOException;
import java.util.function.Consumer;

public class AbstractServiceTest {
    private final int port;

    public AbstractServiceTest() {
        port = (int) (1000 + Math.random() * 5000);
    }

    public String makeResponse(String host, String uri, String paramName, String paramValue) {
        return HttpClient.create()
                .post()
                .uri(host + ":" + port + uri)
                .sendForm((httpClientRequest, httpClientForm) -> httpClientForm.attr(paramName, paramValue))
                .responseContent()
                .aggregate()
                .asString()
                .block();
    }

    public DisposableServer prepPostServer(Consumer<HttpServerRoutes> routBinder, String host) {
        return new AggregationHttpServer(new AggregationServerConfig(host, port, 2))
                .route(routBinder)
                .start();
    }

    public String prepServerAndMakeResponse(String host, String paramValue, Consumer<HttpServerRoutes> routBuilder) {
        var server = prepPostServer(routBuilder, host);
        var result =makeResponse(host, "/", "name", paramValue);
        server.disposeNow();
        return result;
    }

    public String prepServerAndMakeResponse(String host, String paramValue, BindStrategy bindStrategy) {
        return prepServerAndMakeResponse(host, paramValue, new PostRoutBinder("/", bindStrategy).bind());
    }

    public String prepServerAndMakeResponse(String host, String paramValue, RequestHandler<HttpData, String> handler) {
        return prepServerAndMakeResponse(host, paramValue, new PostBindStrategy(handler, new CustomServiceActionFactory<>()));
    }

    public String prepEchoServerAndMakeResponse(String host, String paramValue) {
        RequestHandler<HttpData, String> handler = httpData -> {
            try {
                return Flux.just(new String(httpData.get()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        return prepServerAndMakeResponse(host, paramValue, handler);
    }
}
