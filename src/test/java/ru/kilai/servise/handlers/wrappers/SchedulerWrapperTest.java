package ru.kilai.servise.handlers.wrappers;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.kilai.config.SimplerThreadFactory;
import ru.kilai.servise.handlers.PostParameters;
import ru.kilai.servise.handlers.wrappers.SchedulerWrapper;

import java.io.IOException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SchedulerWrapperTest {

    @Test
    void apply() throws IOException {
        var parameters = Mockito.spy(new PostParameters());
        var httpData = mock(HttpData.class);
        var executorService = Executors.newFixedThreadPool(4,
                new SimplerThreadFactory("worker-"));
        when(httpData.get()).thenReturn("".getBytes());
        when(httpData.getName()).thenReturn("url");
        when(parameters.apply(httpData).publishOn(Schedulers.fromExecutor(executorService)))
                .thenReturn(Flux.just("ans"));

        var stringFlux = new SchedulerWrapper<>(parameters, executorService).apply(httpData);

        assertEquals("ans", stringFlux.blockFirst());

        verify(parameters, times(1)).apply(httpData);
    }
}