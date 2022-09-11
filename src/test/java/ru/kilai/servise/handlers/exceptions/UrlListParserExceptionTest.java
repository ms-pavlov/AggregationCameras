package ru.kilai.servise.handlers.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlListParserExceptionTest {

    @Test
    void check() {
        assertThrows(UrlListParserException.class, () -> {
            throw new UrlListParserException(new Exception());
        });
    }
}