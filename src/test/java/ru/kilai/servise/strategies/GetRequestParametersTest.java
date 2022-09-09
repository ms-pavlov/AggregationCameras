package ru.kilai.servise.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import ru.kilai.server.AggregationHttpServer;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.server.routs.PostBindStrategy;
import ru.kilai.server.routs.PostRoutBinder;
import ru.kilai.servise.CustomServiceActionFactory;
import ru.kilai.util.AbstractServiceTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetRequestParametersTest {
    private static final String TEST_DATA = "same test data";

    @Test
    void apply() {
        var requestParameters = spy(new GetRequestParameters());
        var response = new AbstractServiceTest().prepServerAndMakeResponse("127.0.0.1", TEST_DATA, requestParameters);

        assertEquals(TEST_DATA, response);
    }
}