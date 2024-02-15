package ru.nsu.khamidullin.prime;

import java.util.Arrays;

/**
 * The {@code ConsistentPrimeFinder} class provides a method for finding prime numbers
 * within an array of integers. It extends the {@link PrimeFinder} abstract class.
 */
public class ConsistentPrimeFinder extends PrimeFinder {

    /**
     * Checks if there is at least one prime number in the specified array.
     *
     * @param array an array of integers to be checked for the presence of prime numbers.
     * @return {@code true} if at least one prime number is present, {@code false} otherwise.
     * @see PrimeFinder#isPrime(int)
     */
    @Override
    boolean hasPrime(int[] array) {
        return Arrays.stream(array)
                .anyMatch(PrimeFinder::isPrime);
    }
}