package ru.nsu.khamidullin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.khamidullin.PrimeChecker.isPrime;

import org.junit.jupiter.api.Test;

/**
 * Test class.
 */
public class TestPrimeChecker {
    @Test
    public void isPrimeTestFalse() {
        assertFalse(isPrime(0));
        assertFalse(isPrime(1));
        assertFalse(isPrime(4));
        assertFalse(isPrime(6));
        assertFalse(isPrime(-1));
        assertFalse(isPrime(-4));
        assertFalse(isPrime(-6));
        assertFalse(isPrime(Integer.MIN_VALUE));
    }

    @Test
    public void isPrimeTestTrue() {
        assertTrue(isPrime(2));
        assertTrue(isPrime(-2));
        assertTrue(isPrime(3));
        assertTrue(isPrime(-3));
        assertTrue(isPrime(103));
        assertTrue(isPrime(Integer.MAX_VALUE));

    }
}
