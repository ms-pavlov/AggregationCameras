package ru.kilai.servise.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AggregationHandlerConfigTest {

    @Test
    void prepPostAggregationHandler() {
        assertDoesNotThrow(() -> new AggregationHandlerConfig(-1, -1, -1000)
                .prepPostAggregationHandler());
    }

    @Test
    void prepGetAggregationHandler() {
        assertDoesNotThrow(() -> new AggregationHandlerConfig(-1, -1, -1000)
                .prepGetAggregationHandler());
    }

    @Test
    void getRetryHandler() {
        assertDoesNotThrow(() -> new AggregationHandlerConfig(-1, -1, -1000)
                .getRetryHandler());
    }
}