package ru.kilai.servise.handlers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CameraInfoToJsonStringTest {

    private static final JsonObject OBJECT = Json.createObjectBuilder()
            .add("id", 1).add("urlType", "any")
            .add("videoUrl", "any").add("value", "any")
            .add("ttl", "any").build();


    private static final String jsonString = """
                        
            [
                {
                    "id": 1,
                    "urlType": "any",
                    "videoUrl": "any",
                    "value": "any",
                    "ttl": "any"
                }
            ]""";

    @Test
    void apply() {
        var result = new CameraInfoToJsonString().apply(Flux.just(OBJECT)).blockFirst();

        assertEquals(jsonString, result);

    }
}