package ru.kilai.servise.handlers.factories;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.servise.handlers.RequestHandler;

public interface ServiceHandlerFactory {

    RequestHandler<HttpData, String> createPostParametersHandler();

    RequestHandler<Flux<String>, String> createContentHttpRequestHandler(HttpClient client);
}
