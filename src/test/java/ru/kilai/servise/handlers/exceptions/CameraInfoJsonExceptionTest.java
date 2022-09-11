package ru.kilai.servise.handlers.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CameraInfoJsonExceptionTest {
    @Test
    void check() {
        assertThrows(CameraInfoJsonException.class, () -> {
            throw new CameraInfoJsonException(new Exception());
        });
    }
}