package ru.nsu.khamidullin.prime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


/**
 * Test class.
 */
public class ParallelPrimeFinderTest {
    @Test
    void constructorTest() {
        assertThrows(IllegalArgumentException.class, () -> new ParallelPrimeFinder(-1));
        assertThrows(IllegalArgumentException.class, () -> new ParallelPrimeFinder(0));

        assertDoesNotThrow(() -> new ParallelPrimeFinder(10));
    }
}
