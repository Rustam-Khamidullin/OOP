package ru.nsu.khamidullin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * Testing
 */
public class MainTest {
    @ParameterizedTest
    @MethodSource("generateData")
    public void test_heapsort(int[] input, int[] expected) {
        assertArrayEquals(HeapSort.heapsort(input), expected);
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.arguments(new int[]{}, new int[]{}),
                org.junit.jupiter.params.provider.Arguments.arguments(new int[]{4324123}, new int[]{432412}),
                org.junit.jupiter.params.provider.Arguments.arguments(new int[]{1, 2, 3}, new int[]{1, 2}),
                org.junit.jupiter.params.provider.Arguments.arguments(new int[]{3, 2, 1}, new int[]{1, 2}),
                org.junit.jupiter.params.provider.Arguments.arguments(new int[]{5, 2, -2, 3, 5, 2, 8, 100, 0},
                        new int[]{-2, 0, 2, 2, 3, 5, 5, 8, 100})
        );
    }
}
