package ru.kilai.exeptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BedRequestExceptionTest {

    private static final String TEST_MSG = "test";

    @Test
    void construct() {
        assertDoesNotThrow(() -> new BedRequestException());
    }

    @Test
    void checkMessage() {
        assertEquals(TEST_MSG, new BedRequestException(TEST_MSG).getMessage());
    }

}