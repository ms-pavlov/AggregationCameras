package ru.kilai.servise.handlers.factories;

import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.client.ContentHttpClient;
import ru.kilai.servise.handlers.RequestHandler;

import javax.json.JsonObject;

public interface ServiceHandlerFactory {

    RequestHandler<Flux<ByteBufFlux>, JsonObject> createUrlListParser();

    RequestHandler<Flux<JsonObject>, String> createCameraInfoToJsonString();

    RequestHandler<Flux<String>, ByteBufFlux> createContentHttpRequestHandler(ContentHttpClient client);

    RequestHandler<Flux<JsonObject>, JsonObject> createCameraInfoHttpRequest(ContentHttpClient client);
}
