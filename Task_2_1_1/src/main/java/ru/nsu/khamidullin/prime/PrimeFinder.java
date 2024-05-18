package ru.nsu.khamidullin.prime;

/**
 * The abstract class PrimeFinder provides methods for determining the presence of prime numbers
 * in an array and checking whether a given integer is prime.
 *
 * <p>The {@code hasPrime} method checks if there is at least one prime
 * number in the specified array.
 * The {@code isPrime} method checks if a given integer is a prime number.</p>
 */
public abstract class PrimeFinder {
    /**
     * Checks if there is at least one prime number in the specified array.
     *
     * @param array an array of integers to be checked for the presence of prime numbers.
     * @return {@code true} if at least one prime number is present, {@code false} otherwise.
     */
    abstract boolean hasPrime(int[] array);

    /**
     * Checks whether the given integer is a prime number.
     *
     * @param x the integer to be checked for primality.
     * @return {@code true} if the given integer is prime, {@code false} otherwise.
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