package ru.nsu.khamidullin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.khamidullin.ConsistentSolution.findPrimeConsistent;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Test class.
 */
public class TestConsistentSolution {
    @Test
    public void findPrimeTestTrue() {
        var random = new Random();

        int[] array = new int[100];
        Arrays.fill(array, Integer.MIN_VALUE);
        array[random.nextInt(array.length)] = Integer.MAX_VALUE;

        boolean result = findPrimeConsistent(array);

        assertTrue(result);
    }

    @Test
    public void findPrimeTestFalse() {
        int[] array = new int[100];
        Arrays.fill(array, Integer.MIN_VALUE);

        boolean result = findPrimeConsistent(array);

        assertFalse(result);
    }

    /**
     * Large test.
     */
    public void findPrimeTestLargeFalse() {
        int[] array = new int[1000000];
        Arrays.fill(array, 2147117569);

        long start = System.nanoTime();
        boolean result = findPrimeConsistent(array);
        long end = System.nanoTime();

        assertFalse(result);

        System.out.println("Large False: " + Duration.ofNanos(end - start).toMillis() + " ms");
    }
}
