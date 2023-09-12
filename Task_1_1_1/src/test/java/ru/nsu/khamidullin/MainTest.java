package ru.nsu.khamidullin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    public void test_heapsort() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, Main.heapsort(new int[]{5, 4, 3, 2, 1}));

        assertArrayEquals(new int[]{}, Main.heapsort(new int[]{}));

        assertArrayEquals(new int[]{4324123}, Main.heapsort(new int[]{4324123}));

        assertArrayEquals(new int[]{-2, 0, 2, 2, 3, 5, 5, 8, 100}, Main.heapsort(new int[]{5, 2, -2, 3, 5, 2, 8, 100, 0}));

        assertArrayEquals(new int[]{1, 2, 3}, Main.heapsort(new int[]{1, 2, 3}));

        assertArrayEquals(new int[]{1, 2, 3}, Main.heapsort(new int[]{3, 2, 1}));
    }
}
