package ru.nsu.khamidullin.prime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Test class.
 */
public class StreamPrimeFinderTest {
    @Test
    void constructorTest() {
        assertThrows(IllegalArgumentException.class, () -> new StreamPrimeFinder(-1));
        assertThrows(IllegalArgumentException.class, () -> new StreamPrimeFinder(0));

        assertDoesNotThrow(() -> new StreamPrimeFinder(10));
    }
}
