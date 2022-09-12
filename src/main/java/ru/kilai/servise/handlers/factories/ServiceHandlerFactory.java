package ru.kilai.servise.handlers.factories;

import io.netty.handler.codec.http.multipart.HttpData;
import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.client.ContentHttpClient;
import ru.kilai.servise.handlers.RequestHandler;

import javax.json.JsonObject;
import java.util.List;
import java.util.Map;

public interface ServiceHandlerFactory {

    RequestHandler<Map.Entry<String, List<String>>, String> createGetParameters();

    RequestHandler<HttpData, String> createPostParameters();

    RequestHandler<Flux<ByteBufFlux>, JsonObject> createUrlListParser();

    RequestHandler<Flux<JsonObject>, String> createCameraInfoToJsonString();

    RequestHandler<Flux<String>, ByteBufFlux> createContentHttpRequestHandler(ContentHttpClient client);

    RequestHandler<Flux<JsonObject>, JsonObject> createCameraInfoHttpRequest(ContentHttpClient client);
}
