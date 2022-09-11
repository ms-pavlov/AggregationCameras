package ru.kilai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AggregationApplicationTest {

    @Test
    void main() {
        assertDoesNotThrow(() -> AggregationApplication.main("test"));
    }
}