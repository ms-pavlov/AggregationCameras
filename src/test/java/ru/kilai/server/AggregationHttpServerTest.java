package ru.kilai.server;

import org.junit.jupiter.api.Test;
import ru.kilai.server.config.AggregationServerConfig;
import ru.kilai.util.AbstractServiceTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregationHttpServerTest extends AbstractServiceTest {
    private static final String TEST_DATA = "same test data";

    @Test
    void checkStartAndStop() {
        var server = new AggregationHttpServer(new AggregationServerConfig());
        assertDoesNotThrow(server::start);
        assertDoesNotThrow(server::stop);
    }

    @Test
    void checkRoute() {
        String host = "127.0.0.1";

        var response = new AbstractServiceTest()
                .prepEchoServerAndMakeResponse(host, TEST_DATA);

        assertEquals(TEST_DATA, response);
    }

}