package ru.kilai.servise.handlers.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BedPostParametersExceptionTest {

    @Test
    void check() {
        assertThrows(BadPostParametersException.class, () -> {
            throw new BadPostParametersException(new Exception());
        });
    }
}