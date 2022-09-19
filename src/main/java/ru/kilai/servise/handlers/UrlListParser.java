package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.servise.handlers.exceptions.UrlListParserException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonValue;


public class UrlListParser implements RequestHandler<Flux<ByteBufFlux>, JsonObject> {
    @Override
    public Flux<JsonObject> apply(Flux<ByteBufFlux> flux) {
        return flux.flatMap(ByteBufFlux::asInputStream)
                .flatMap(inputStream -> {
                    try (var jsonReader = Json.createReader(inputStream)) {
                        var jsonArray = jsonReader.read().asJsonArray();
                        return Flux.fromStream(jsonArray.stream())
                                .map(JsonValue::asJsonObject)
                                .map(jsonObject -> Json.createObjectBuilder()
                                        .add("order", jsonArray.indexOf(jsonObject))
                                        .add("id", jsonObject.get("id"))
                                        .add("sourceDataUrl", jsonObject.get("sourceDataUrl"))
                                        .add("tokenDataUrl", jsonObject.get("tokenDataUrl"))
                                        .build());
                    } catch (JsonException err) {
                        return Flux.error(() -> new UrlListParserException(err));
                    }
                });
    }
}
