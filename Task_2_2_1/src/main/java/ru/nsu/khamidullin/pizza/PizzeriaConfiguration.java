package ru.nsu.khamidullin.pizza;

import java.util.List;

public record PizzeriaConfiguration(
        List<Integer> bakersCookingTime,
        List<Integer> deliveriesCapacity,
        int storageCapacity
) {
}
