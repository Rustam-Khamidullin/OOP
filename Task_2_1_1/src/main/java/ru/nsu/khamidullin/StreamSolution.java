package ru.nsu.khamidullin;

import java.util.Arrays;

/**
 * The {@code StreamSolution} class provides a solution for finding prime numbers
 * within an array of integers using Java streams in parallel.
 */
public class StreamSolution {
    /**
     * Finds if there is a prime number in the given array using Java streams in parallel.
     *
     * @param array an array of integers
     * @return {@code true} if a prime number is found, {@code false} otherwise
     */
    public static boolean findPrimeStream(int[] array) {
        return Arrays.stream(array)
                .parallel()
                .anyMatch(PrimeChecker::isPrime);
    }
}
