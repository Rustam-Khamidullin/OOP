package ru.nsu.khamidullin.prime;

import java.util.Arrays;

/**
 * The {@code StreamPrimeFinder} class provides a solution for finding prime numbers
 * within an array of integers using Java streams in parallel. It extends the {@link PrimeFinder} abstract class.
 */
public class StreamPrimeFinder extends PrimeFinder {
    private final int threadNumber;

    /**
     * Constructs a new {@code StreamPrimeFinder} with the specified number of threads.
     *
     * @param threadNumber the number of threads to be used for parallel processing.
     * @throws IllegalArgumentException if the specified thread number is not positive.
     */
    public StreamPrimeFinder(int threadNumber) {
        if (threadNumber <= 0) {
            throw new IllegalArgumentException("Thread number must be positive");
        }

        this.threadNumber = threadNumber;
    }

    /**
     * Checks if there is at least one prime number in the specified array using Java streams in parallel.
     *
     * @param array an array of integers to be checked for the presence of prime numbers.
     * @return {@code true} if at least one prime number is present, {@code false} otherwise.
     */
    @Override
    public boolean hasPrime(int[] array) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(threadNumber));

        try {
            return Arrays.stream(array)
                    .parallel()
                    .anyMatch(PrimeFinder::isPrime);
        } finally {
            System.clearProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
        }
    }
}