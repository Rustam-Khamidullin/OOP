package ru.nsu.khamidullin.pizza;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Pizzeria implements Runnable {
    private static final String PIZZERIA_CONFIGURATION = "pizzeria.json";
    private final int workingTime;
    private final BlockingQueue<Integer> orders;
    private final BlockingQueue<Integer> storage;
    private PizzeriaConfiguration pizzeriaConfiguration;
    private final List<Backer> backers;
    private final List<Deliveryman> deliverers;
    private final List<Thread> bakerThreads;
    private final List<Thread> deliveryThreads;


    public Pizzeria(int workingTime) throws IOException, IllegalAccessException {
        setPizzeriaConfiguration();

        this.workingTime = workingTime;

        orders = new BlockingQueue<>();
        storage = new BlockingQueue<>(pizzeriaConfiguration.getStorageCapacity());

        backers = new ArrayList<>();
        deliverers = new ArrayList<>();
        bakerThreads = new ArrayList<>();
        deliveryThreads = new ArrayList<>();

        for (var cookingTime : pizzeriaConfiguration.getBakersCookingTime()) {
            var newBaker = new Backer(cookingTime, orders, storage);
            backers.add(newBaker);
            bakerThreads.add(new Thread(newBaker));
        }

        for (var capacity : pizzeriaConfiguration.getDeliveriesCapacity()) {
            var newDeliveryman = new Deliveryman(capacity, storage);
            deliverers.add(newDeliveryman);
            deliveryThreads.add(new Thread(newDeliveryman));
        }
    }

    @Override
    public void run() {
        startBakerWorking();
        startDeliverymanWorking();

        try {
            Thread.sleep(workingTime);

            stopBakerWorking();
            stopDeliverymanWorking();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrder(int id) throws InterruptedException {
        orders.push(id);
    }

    private void startBakerWorking() {
        for (var bakerThread : bakerThreads) {
            bakerThread.start();
        }
    }

    private void startDeliverymanWorking() {
        for (var deliverymanThread : deliveryThreads) {
            deliverymanThread.start();
        }
    }

    private void stopBakerWorking() throws InterruptedException {
        for (var baker : backers) {
            baker.stopWorking();
        }
        for (var bakerThread : bakerThreads) {
            bakerThread.join();
        }
    }

    private void stopDeliverymanWorking() throws InterruptedException {
        for (var deliveryman : deliverers) {
            deliveryman.stopWorking();
        }
        for (var deliverymanThread : deliveryThreads) {
            deliverymanThread.join();
        }
    }

    private void setPizzeriaConfiguration() throws IOException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();

        PizzeriaConfiguration pizzeriaConfiguration;
        try (InputStream fileInputStream = ClassLoader.getSystemResourceAsStream(PIZZERIA_CONFIGURATION)) {
            pizzeriaConfiguration =
                    objectMapper.readValue(fileInputStream, PizzeriaConfiguration.class);
        }

        List<Integer> bakersCookingTime = pizzeriaConfiguration.getBakersCookingTime();
        List<Integer> deliveriesCapacity = pizzeriaConfiguration.getDeliveriesCapacity();
        int storageCapacity = pizzeriaConfiguration.getStorageCapacity();

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

        this.pizzeriaConfiguration = pizzeriaConfiguration;
    }
}
