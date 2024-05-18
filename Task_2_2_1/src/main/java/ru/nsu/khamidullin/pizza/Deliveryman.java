package ru.nsu.khamidullin.pizza;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The {@code Deliveryman} class represents a thread simulating a deliveryman in a pizzeria.
 * It processes pizza deliveries by retrieving pizzas from the storage and delivering
 * them within a specified capacity.
 */
public class Deliveryman extends Thread {
    private static final Logger logger = LogManager.getLogger(Deliveryman.class);
    private static final int DELIVERY_TIME = 5000;
    private final BlockingQueue<Integer> storage;
    private final int capacity;

    /**
     * Constructs a Deliveryman with the specified delivery capacity and storage queue.
     *
     * @param capacity The maximum number of pizza deliveries
     *                 the deliveryman can make in one round.
     * @param storage  The queue containing pizzas to be delivered.
     */
    public Deliveryman(int capacity, BlockingQueue<Integer> storage) {
        this.capacity = capacity;
        this.storage = storage;
    }

    /**
     * Runs the deliveryman thread, continuously processing pizza deliveries
     * by retrieving from storage and delivering.
     */
    @Override
    public void run() {
        while (!interrupted()) {
            int current = 0;
            do {
                int id;
                try {
                    id = storage.pop();
                } catch (InterruptedException e) {
                    return;
                }

                logger.info(id + " заказ доставляется\n");
                current++;
            } while (current < capacity && !storage.isEmpty() && !interrupted());

            try {
                Thread.sleep(DELIVERY_TIME);
            } catch (InterruptedException ignored) {
                return;
            }
        }
    }
}