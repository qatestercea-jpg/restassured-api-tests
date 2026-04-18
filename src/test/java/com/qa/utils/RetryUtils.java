package com.qa.utils;

import java.util.function.Predicate;
import java.util.function.Supplier;

public final class RetryUtils {

    private RetryUtils() {
    }

    public static <T> T executeWithRetry(Supplier<T> supplier, Predicate<T> shouldRetry, String action) {
        final int maxAttempts = 3;
        final long retryDelayMs = 1000L;

        T result = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            result = supplier.get();
            if (!shouldRetry.test(result)) {
                return result;
            }

            if (attempt < maxAttempts) {
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        return result;
    }
}
