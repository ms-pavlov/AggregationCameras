package ru.kilai.servise.handlers;

import reactor.core.publisher.Flux;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class CameraInfoToJsonString implements RequestHandler<Flux<JsonObject>, String> {
    private static final List<String> FIELDS = List.of("id", "urlType", "videoUrl", "value", "ttl");

    @Override
    public Flux<String> apply(Flux<JsonObject> listFlux) {
        return listFlux
                .map(this::filter)
                .collect(Json::createArrayBuilder,
                        (jsonArrayBuilder, jsonObject) -> jsonArrayBuilder
                                .add(Json.createObjectBuilder(jsonObject))
                )
                .map(JsonArrayBuilder::build)
                .map(this::getPrettyPrintJson)
                .flatMapMany(Flux::just);
    }

    private JsonObject filter(JsonObject jsonObject) {
        var result = Json.createObjectBuilder();
        FIELDS.forEach(field -> result.add(field, jsonObject.get(field)));
        return result.build();
    }

    private String getPrettyPrintJson(JsonValue jsonValues) {
        StringWriter writer = new StringWriter();
        var properties = Map.of(JsonGenerator.PRETTY_PRINTING, true);
        JsonGeneratorFactory jsonGeneratorFactory = Json.createGeneratorFactory(properties);
        try (JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(writer)) {
            jsonGenerator.write(jsonValues).close();
            return writer.toString();
        }
    }
}
