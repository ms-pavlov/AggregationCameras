package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;
import ru.kilai.client.ContentHttpClient;
import ru.kilai.servise.handlers.exceptions.CameraInfoJsonException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.InputStream;
import java.util.Comparator;

public class CameraInfoHttpRequest implements RequestHandler<Flux<JsonObject>, JsonObject> {
    private final ContentHttpClient client;

    public CameraInfoHttpRequest(ContentHttpClient client) {
        this.client = client;
    }

    @Override
    public Flux<JsonObject> apply(Flux<JsonObject> jsonObjectFlux) {
        return jsonObjectFlux
                .flatMap(jsonObject -> Flux.merge(
                                Flux.just(jsonObject),
                                getDataUrl(jsonObject.getString("sourceDataUrl")),
                                getDataUrl(jsonObject.getString("tokenDataUrl")))
                        .collect(Json::createObjectBuilder,
                                (jsonArrayBuilder, jsonObject1) ->
                                        jsonObject1.keySet().forEach(s -> jsonArrayBuilder.add(s, jsonObject1.get(s)))
                        )
                        .map(JsonObjectBuilder::build)
                        .flatMapMany(Flux::just))
                .sort(Comparator.comparingInt(value -> value.getInt("order")));
    }

    private Flux<JsonObject> getDataUrl(String dataUrl) {
        return client
                .get(dataUrl)
                .asInputStream()
                .map(this::parsToJsonArray);
    }

    private JsonObject parsToJsonArray(InputStream inputStream) {
        try (var jsonReader = Json.createReader(inputStream)) {
            return jsonReader.read().asJsonObject();
        } catch (JsonException err) {
            throw new CameraInfoJsonException(err);
        }
    }
}
