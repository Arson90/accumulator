package com.metapack.assignments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccumulatorImplTest {
    private Accumulator accumulator;

    @BeforeEach
    public void setUp() {
        accumulator = new AccumulatorImpl();
    }

    @Test
    void shouldReturnSevenAsExpectedAfterAccumulateValues() {
        final int result = accumulator.accumulate(1, 2, 4);
        assertEquals(7, result);
    }

    @Test
    void shouldReturnTotalTwentyAsExpectedAfterRunTasksInDifferentThreads() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> accumulator.accumulate(1, 2, 4));
        executorService.submit(() -> accumulator.accumulate(4));
        executorService.submit(() -> accumulator.accumulate(2, 6));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        final int result = accumulator.getTotal();
        assertEquals(19, result);
    }

    @Test
    void shouldReturnTotalZeroAsExpectedAfterResetValues() {
        final int result = accumulator.accumulate(1, 2, 4);
        assertEquals(7, result);

        accumulator.reset();

        final int total = accumulator.getTotal();
        assertEquals(0, total);
    }
}