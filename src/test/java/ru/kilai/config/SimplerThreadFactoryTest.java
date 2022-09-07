package ru.kilai.config;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimplerThreadFactoryTest {
    private static final String TEST_MSG = "Ок";
    private final List<String> test = new ArrayList<>();

    @Test
    void newThread() throws InterruptedException {
        var thread = new SimplerThreadFactory("test").newThread(() -> test.add(TEST_MSG));
        thread.start();
        thread.join();

        assertEquals(1, test.size());
        assertEquals(TEST_MSG, test.get(0));
    }
}