package com.metapack.assignments;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class AccumulatorImpl implements Accumulator {
    private AtomicInteger values = new AtomicInteger(0);
    @Override
    public int accumulate(int... values) {
        int sum = Arrays.stream(values).sum();
        this.values.getAndAdd(sum);
        return sum;
    }

    @Override
    public int getTotal() {
        return this.values.get();
    }

    @Override
    public void reset() {
        this.values.set(0);
    }
}
