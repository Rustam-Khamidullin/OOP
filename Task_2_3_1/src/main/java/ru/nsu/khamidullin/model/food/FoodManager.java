package ru.nsu.khamidullin.model.food;

import ru.nsu.khamidullin.model.Coordinate;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FoodManager {
    private final int foodNumber;
    private int food;

    public FoodManager(int foodNumber) {
        this.foodNumber = foodNumber;
        food = 0;
    }

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


    public void eat() {
        food--;
    }
}
