package ru.nsu.khamidullin.pizza;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code Pizzeria} class represents a simulated pizzeria with bakers and deliverymen,
 * handling pizza orders and deliveries.
 * <p>
 * It manages the pizzeria's configuration, orders, and storage using {@code PizzeriaConfiguration},
 * {@code BlockingQueue}, and {@code PizzeriaState}.
 * </p>
 * <p>
 * The class provides methods to start and stop the working threads, add orders to the pizzeria,
 * and load/save the state of the pizzeria to a JSON file.
 * </p>
 * <p>
 * This class extends {@code Thread} to allow the pizzeria to run concurrently with other parts
 * of a larger simulation or application.
 * </p>
 */
public class Pizzeria extends Thread {
    private static final String PIZZERIA_STATE = "pizzeriaState.json";
    private static final String PIZZERIA_CONFIGURATION = "pizzeria.json";
    private final int workingTime;
    private BlockingQueue<Integer> orders;
    private BlockingQueue<Integer> storage;
    private PizzeriaConfiguration pizzeriaConfiguration;
    private final List<Thread> bakerThreads;
    private final List<Thread> deliveryThreads;


    /**
     * Constructs a new {@code Pizzeria} instance with the specified working time.
     *
     * @param workingTime The duration the pizzeria will be operational (in milliseconds).
     * @throws IOException           If an I/O error occurs while loading configuration or orders.
     * @throws IllegalAccessException If the loaded configuration is invalid.
     */
    public Pizzeria(int workingTime) throws IOException, IllegalAccessException {
        setPizzeriaConfiguration();
        loadOrders();

        this.workingTime = workingTime;

        bakerThreads = new ArrayList<>();
        deliveryThreads = new ArrayList<>();

        for (var cookingTime : pizzeriaConfiguration.getBakersCookingTime()) {
            bakerThreads.add(new Baker(cookingTime, orders, storage));
        }

        for (var capacity : pizzeriaConfiguration.getDeliveriesCapacity()) {
            deliveryThreads.add(new Deliveryman(capacity, storage));
        }
    }

    /**
     * Runs the pizzeria simulation. Starts working threads, runs for the specified duration,
     * stops the working threads, prints a message indicating the end of work, and saves the state to a file.
     */
    @Override
    public void run() {
        startWorking();

        try {
            sleep(workingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            stopWorking();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Работа окончена");

        try {
            saveOrders();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a pizza order to the pizzeria.
     *
     * @param id The identifier of the pizza order.
     * @throws InterruptedException If the thread is interrupted while adding the order to the queue.
     */
    public void addOrder(int id) throws InterruptedException {
        orders.push(id);
    }

    /**
     * Starts the working threads for bakers and deliverymen.
     */
    private void startWorking() {
        for (var bakerThread : bakerThreads) {
            bakerThread.start();
        }
        for (var deliverymanThread : deliveryThreads) {
            deliverymanThread.start();
        }
    }

    /**
     * Stops the working threads for bakers and deliverymen by interrupting them and waiting for their completion.
     *
     * @throws InterruptedException If any interruption occurs while waiting for threads to complete.
     */
    private void stopWorking() throws InterruptedException {
        for (var bakerThread : bakerThreads) {
            bakerThread.interrupt();
        }
        for (var deliverymanThread : deliveryThreads) {
            deliverymanThread.interrupt();
        }
        for (var bakerThread : bakerThreads) {
            bakerThread.join();
        }
        for (var deliverymanThread : deliveryThreads) {
            deliverymanThread.join();
        }
    }

    /**
     * Loads orders and initializes the orders and storage queues based on the stored pizzeria state.
     *
     * @throws IOException If an I/O error occurs during the loading process.
     */
    private void loadOrders() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        PizzeriaState pizzeriaState;

        orders = new BlockingQueue<>();
        storage = new BlockingQueue<>(pizzeriaConfiguration.getStorageCapacity());

        try (FileInputStream fileInputStream = new FileInputStream(PIZZERIA_STATE)) {
            pizzeriaState =
                    objectMapper.readValue(fileInputStream, PizzeriaState.class);
        }

        if (pizzeriaState.getStorage().size() > storage.getCapacity()) {
            throw new IOException("Переполнение склада");
        }

        orders.setQueue(pizzeriaState.getOrders());
        storage.setQueue(pizzeriaState.getStorage());
    }

    /**
     * Saves the current orders and storage state to a file.
     *
     * @throws IOException If an I/O error occurs during the saving process.
     */
    private void saveOrders() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        PizzeriaState pizzeriaState = new PizzeriaState();
        pizzeriaState.setOrders(orders.getQueue());
        pizzeriaState.setStorage(storage.getQueue());

        try (FileOutputStream outputStream = new FileOutputStream(PIZZERIA_STATE)) {
            objectMapper.writeValue(outputStream, pizzeriaState);
        }
    }

    /**
     * Reads and sets the pizzeria configuration from a configuration file.
     *
     * @throws IOException            If an I/O error occurs during the configuration loading process.
     * @throws IllegalAccessException If the loaded configuration is invalid or incomplete.
     */
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
