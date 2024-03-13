package ru.nsu.khamidullin.pizza;

import java.util.Queue;

/**
 * The {@code PizzeriaState} class represents the state of a pizzeria,
 * including the current orders and storage.
 * It encapsulates two queues, one for orders and one for storage,
 * to manage the flow of pizza orders and delivery.
 * This class is designed to be used in a multi-threaded environment,
 * potentially by different components such as bakers, deliverymen,
 * and other parts of a pizzeria simulation.
 */
public class PizzeriaState {
    private Queue<Integer> orders;
    private Queue<Integer> storage;

    /**
     * Gets the queue containing the current pizza orders.
     *
     * @return The queue of pizza orders.
     */
    public Queue<Integer> getOrders() {
        return orders;
    }

    /**
     * Sets the queue containing the pizza orders.
     *
     * @param orders The queue of pizza orders to set.
     */
    public void setOrders(Queue<Integer> orders) {
        this.orders = orders;
    }

    /**
     * Gets the queue containing the storage of delivered pizzas.
     *
     * @return The queue representing the storage of delivered pizzas.
     */
    public Queue<Integer> getStorage() {
        return storage;
    }

    /**
     * Sets the queue containing the storage of delivered pizzas.
     *
     * @param storage The queue representing the storage of delivered pizzas to set.
     */
    public void setStorage(Queue<Integer> storage) {
        this.storage = storage;
    }
}
