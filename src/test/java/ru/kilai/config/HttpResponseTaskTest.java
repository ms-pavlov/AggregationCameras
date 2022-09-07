package ru.kilai.config;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import ru.kilai.config.exeptions.BedRequestException;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HttpResponseTaskTest {
    private static final String TEST_MSG = "тест";

    private HttpServerRequest request;
    private HttpServerResponse response;

    @BeforeEach
    void setUp() {
        request = mock(HttpServerRequest.class);
        response = mock(HttpServerResponse.class);
    }

    @Test
    void checkConstructor() {
        assertThrows(BedRequestException.class, () -> new HttpResponseTask(null, null));

        assertDoesNotThrow(() -> new HttpResponseTask(request, response));
    }

    @Test
    void checkExecute() {
        HttpData data = mock(HttpData.class);
        var form = Flux.just(data);
        when(request.receiveForm()).thenReturn(form);

        Function<HttpData, Mono<String>> function = httpData -> Mono.just(TEST_MSG);

        new HttpResponseTask(request, response).execute(function);

        verify(request, times(1)).receiveForm();
    }

}