package ru.nsu.khamidullin.prime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Test class.
 */
public class PrimeFinderTest {
    @ParameterizedTest
    @MethodSource("generateData")
    void primeFinderSimpleTest(PrimeFinder primeFinder) {
        var random = new Random();
        int[] array = new int[1000];


        Arrays.fill(array, Integer.MIN_VALUE);
        assertFalse(primeFinder.hasPrime(array));


        array[random.nextInt(array.length)] = Integer.MAX_VALUE;
        assertTrue(primeFinder.hasPrime(array));
    }

    @ParameterizedTest
    @MethodSource("generateData")
    void primeFinderLargeFalseTest(PrimeFinder primeFinder) {
        int[] array = new int[100000];
        Arrays.fill(array, 2147117569);

        assertFalse(primeFinder.hasPrime(array));
    }

    @ParameterizedTest
    @MethodSource("generateData")
    void primeFinderLargeTrueTest(PrimeFinder primeFinder) {
        var random = new Random();
        int[] array = new int[100000];
        Arrays.fill(array, 2147117569);
        array[random.nextInt(array.length)] = Integer.MAX_VALUE;

        assertTrue(primeFinder.hasPrime(array));
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.arguments(new ConsistentPrimeFinder()),
                Arguments.arguments(new ParallelPrimeFinder(12)),
                Arguments.arguments(new StreamPrimeFinder(12))
        );
    }
}
