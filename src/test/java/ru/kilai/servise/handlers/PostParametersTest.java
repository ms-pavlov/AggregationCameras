package ru.kilai.servise.handlers;

import io.netty.handler.codec.http.multipart.HttpData;
import org.junit.jupiter.api.Test;
import ru.kilai.servise.handlers.exceptions.BadPostParametersException;
import ru.kilai.util.AbstractServiceTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PostParametersTest {
    private static final String TEST_DATA = "same test data";

    @Test
    void apply() {
        var requestParameters = spy(new PostParameters());
        var response = new AbstractServiceTest().prepServerAndMakeResponse("127.0.0.1", TEST_DATA, requestParameters);

        assertEquals(TEST_DATA, response);
    }

    @Test
    void exception() throws IOException {
        var httpData = mock(HttpData.class);
        when(httpData.get()).thenThrow(new IOException());

        var requestParameters = spy(new PostParameters());
        assertThrows(BadPostParametersException.class, () -> requestParameters.apply(httpData));
    }

}