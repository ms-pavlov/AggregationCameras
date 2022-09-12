package ru.kilai.servise.handlers.wrappers;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import ru.kilai.servise.handlers.PostParameters;
import ru.kilai.servise.handlers.wrappers.LogWrapper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogWrapperTest {

    @Test
    void apply() throws IOException {
        var parameters = Mockito.spy(new PostParameters());
        var httpData = mock(HttpData.class);
        when(httpData.get()).thenReturn("".getBytes());
        when(httpData.getName()).thenReturn("url");
        when(parameters.apply(httpData).log()).thenReturn(Flux.just("ans"));

        var stringFlux = new LogWrapper<>(parameters).apply(httpData);

        assertEquals("ans", stringFlux.blockFirst());

        verify(parameters, times(1)).apply(httpData);
    }
}