package ru.nsu.khamidullin.model.food;

import ru.nsu.khamidullin.model.Coordinate;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible for managing food in the game.
 */
public class FoodManager {
    private final int foodNumber;
    private int food;

    /**
     * Constructs a FoodManager with the specified number of food items.
     *
     * @param foodNumber the number of food items to be managed.
     */
    public FoodManager(int foodNumber) {
        this.foodNumber = foodNumber;
        food = 0;
    }

    /**
     * Generates food items at random empty coordinates from the provided list.
     *
     * @param emptyCoordinates a list of empty coordinates where food can be generated.
     * @return a list of coordinates where food is generated.
     */
    public List<Coordinate> generateFood(List<Coordinate> emptyCoordinates) {
        List<Coordinate> generatedFood = new LinkedList<>();

        while (food < foodNumber && !emptyCoordinates.isEmpty()) {
            int index = ThreadLocalRandom.current().nextInt(emptyCoordinates.size());

            generatedFood.add(emptyCoordinates.get(index));
            emptyCoordinates.remove(index);

            food++;
        }

        return generatedFood;
    }

    /**
     * Decrements the count of available food items when one is eaten.
     */
    public void eat() {
        food--;
    }
}
