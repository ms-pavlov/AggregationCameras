package ru.kilai.servise.handlers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import ru.kilai.servise.handlers.exceptions.UrlListParserException;

import static org.junit.jupiter.api.Assertions.*;

class UrlListParserTest {

    private static final String jsonString = """
            [
                {
                    "id": 1,
                    "sourceDataUrl": "testSource",
                    "tokenDataUrl": "testToken"
                }
            ]""";

    @Test
    void apply() {
        var handler = new UrlListParser();

        var objectList = handler
                .apply(Flux.just(ByteBufFlux.fromString(Flux.just(jsonString))))
                .collectList()
                .block();

        assertNotNull(objectList);
        assertEquals(1, objectList.size());

        assertEquals(1, objectList.get(0).getInt("id"));
        assertEquals("testSource", objectList.get(0).getString("sourceDataUrl"));
        assertEquals("testToken", objectList.get(0).getString("tokenDataUrl"));
    }

    @Test
    void checkException() {
        var handler = new UrlListParser();

        assertThrows(UrlListParserException.class, () -> handler
                .apply(Flux.just(ByteBufFlux.fromString(Flux.just("{{{{"))))
                .collectList()
                .block());
    }

}