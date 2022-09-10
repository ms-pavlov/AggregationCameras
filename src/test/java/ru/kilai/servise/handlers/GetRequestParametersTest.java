package ru.kilai.servise.handlers;

import org.junit.jupiter.api.Test;
import ru.kilai.util.AbstractServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetRequestParametersTest {
    private static final String TEST_DATA = "same test data";

    @Test
    void apply() {
        var requestParameters = spy(new PostParameters());
        var response = new AbstractServiceTest().prepServerAndMakeResponse("127.0.0.1", TEST_DATA, requestParameters);

        assertEquals(TEST_DATA, response);
    }
}