package ru.nsu.khamidullin.model.food;

import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.model.Coordinate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class.
 */
class FoodManagerTest {

    @Test
    void testGenerateFood() {
        FoodManager foodManager = new FoodManager(3);
        List<Coordinate> emptyCoordinates = new LinkedList<>(Arrays.asList(
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(1, 0),
                new Coordinate(1, 1)
        ));

        List<Coordinate> generatedFood = foodManager.generateFood(emptyCoordinates);
        assertEquals(3, generatedFood.size());

        for (Coordinate foodCoordinate : generatedFood) {
            assertFalse(emptyCoordinates.contains(foodCoordinate));
        }
    }
}