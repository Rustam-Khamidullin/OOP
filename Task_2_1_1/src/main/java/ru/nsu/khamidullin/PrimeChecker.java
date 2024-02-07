package ru.nsu.khamidullin;

/**
 * The {@code PrimeChecker} class provides a utility for determining whether
 * a given integer is a prime number.
 */
public class PrimeChecker {
    /**
     * Checks whether the given integer is a prime number.
     *
     * @param x the integer to be checked for primality
     * @return {@code true} if the integer is prime, {@code false} otherwise
     */
    public static boolean isPrime(int x) {
        if (Math.abs((long) x) <= 1) {
            return false;
        }
        int sqrt = (int) Math.floor(Math.sqrt(Math.abs((long) x)));
        for (int i = 2; i <= sqrt; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
