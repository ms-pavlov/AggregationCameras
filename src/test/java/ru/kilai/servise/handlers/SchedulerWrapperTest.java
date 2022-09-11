package ru.kilai.servise.handlers;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.kilai.config.SimplerThreadFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SchedulerWrapperTest {

    @Test
    void apply() throws IOException {
        var parameters = spy(new PostParameters());
        var httpData = mock(HttpData.class);
        var executorService = Executors.newFixedThreadPool(4,
                new SimplerThreadFactory("worker-"));
        when(httpData.get()).thenReturn("".getBytes());
        when(parameters.apply(httpData).publishOn(Schedulers.fromExecutor(executorService)))
                .thenReturn(Flux.just("ans"));

        var stringFlux = new SchedulerWrapper<>(parameters, executorService).apply(httpData);

        assertEquals("ans", stringFlux.blockFirst());

        verify(parameters, times(1)).apply(httpData);
    }
}