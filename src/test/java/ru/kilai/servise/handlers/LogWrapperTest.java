package ru.kilai.servise.handlers;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogWrapperTest {

    @Test
    void apply() throws IOException {
        var parameters = spy(new PostParameters());
        var httpData = mock(HttpData.class);
        when(httpData.get()).thenReturn("".getBytes());
        when(parameters.apply(httpData).log()).thenReturn(Flux.just("ans"));

        var stringFlux = new LogWrapper<>(parameters).apply(httpData);

        assertEquals("ans", stringFlux.blockFirst());

        verify(parameters, times(1)).apply(httpData);
    }
}