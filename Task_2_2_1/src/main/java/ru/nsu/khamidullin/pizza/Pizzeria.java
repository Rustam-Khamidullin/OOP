package ru.nsu.khamidullin.pizza;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Pizzeria {
    private final BlockingQueue<Integer> orders;
    private final BlockingQueue<Integer> storage;
    private List<Integer> bakersCookingTime;
    private List<Integer> deliveriesCapacity;
    private int storageCapacity;
    private final List<Thread> bakers;
    private final List<Thread> deliverers;


    public Pizzeria() throws IOException, IllegalAccessException {
        setPizzeriaConfiguration();

        orders = new BlockingQueue<>();
        storage = new BlockingQueue<>(storageCapacity);

        bakers = new ArrayList<>();
        deliverers = new ArrayList<>();

        for (var cookingTime : bakersCookingTime) {
            bakers.add(new Thread(new Backer(cookingTime, orders, storage)));
        }

        for (var capacity : deliveriesCapacity) {
            deliverers.add(new Thread(new Deliveryman(capacity, storage)));
        }
    }

    public void runPizzeria() {
        startBakerWorking();
        startDeliverymanWorking();
    }

    public void addOrder(int id) throws InterruptedException {
        orders.push(id);
    }

    private void startBakerWorking() {
        for (var baker : bakers) {
            baker.start();
        }
    }

    private void startDeliverymanWorking() {
        for (var deliveryman : deliverers) {
            deliveryman.start();
        }
    }

    private void setPizzeriaConfiguration() throws IOException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream fileInputStream = ClassLoader.getSystemResourceAsStream("pizzeria.json")) {
            PizzeriaConfiguration pizzeriaConfiguration =
                    objectMapper.readValue(fileInputStream, PizzeriaConfiguration.class);

            List<Integer> bakersCookingTime = pizzeriaConfiguration.bakersCookingTime();
            List<Integer> deliveriesCapacity = pizzeriaConfiguration.deliveriesCapacity();
            int storageCapacity = pizzeriaConfiguration.storageCapacity();

            if (deliveriesCapacity.isEmpty()
                    || bakersCookingTime.isEmpty()
                    || storageCapacity <= 0) {
                throw new IllegalAccessException();
            }
            for (var i : bakersCookingTime) {
                if (i <= 0) {
                    throw new IllegalAccessException();
                }
            }
            for (var i : deliveriesCapacity) {
                if (i <= 0) {
                    throw new IllegalAccessException();
                }
            }

            this.bakersCookingTime = bakersCookingTime;
            this.deliveriesCapacity = deliveriesCapacity;
            this.storageCapacity = storageCapacity;
        }
    }
}
