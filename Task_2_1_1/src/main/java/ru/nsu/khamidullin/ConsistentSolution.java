package ru.nsu.khamidullin;

import static ru.nsu.khamidullin.PrimeChecker.isPrime;

/**
 *  The {@code ConsistentSolution} class provides a method for finding prime numbers
 *  within an array of integers.
 */
public class ConsistentSolution {
    /**
     * Finds if there is a prime number in the given array.
     *
     * @param array an array of integers
     * @return {@code true} if a prime number is found, {@code false} otherwise
     */
    public static boolean findPrimeConsistent(int[] array) {
        for (var elem : array) {
            if (isPrime(elem)) {
                return true;
            }
        }
        return false;
    }
}