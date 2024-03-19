package ru.nsu.khamidullin.pizza;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The {@code Baker} class represents a thread simulating a baker in a pizzeria.
 * It processes pizza orders by cooking them and transferring them to the storage queue.
 */
public class Baker extends Thread {
    private static final Logger logger = LogManager.getLogger(Baker.class);
    private final BlockingQueue<Integer> orders;
    private final BlockingQueue<Integer> storage;
    private final int cookingTime;

    /**
     * Constructs a Baker with the specified cooking time, order queue, and storage queue.
     *
     * @param cookingTime The time it takes for the baker to cook a pizza order.
     * @param orders      The queue containing pizza orders for processing.
     * @param storage     The queue where the baked pizzas are stored.
     */
    public Baker(int cookingTime, BlockingQueue<Integer> orders, BlockingQueue<Integer> storage) {
        this.cookingTime = cookingTime;
        this.orders = orders;
        this.storage = storage;
    }

    /**
     * Runs the baker thread, continuously processing pizza orders by cooking
     * and transferring them to storage.
     */
    @Override
    public void run() {
        while (!interrupted()) {
            int order;
            try {
                order = orders.pop();
            } catch (InterruptedException e) {
                return;
            }
            logger.info("%d заказ готовится\n".formatted(order));

            try {
                Thread.sleep(cookingTime);
                storage.push(order);

            } catch (InterruptedException e) {
                try {
                    orders.push(order);
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Baker was interrupted second time");
                }

                logger.info("%d заказ не был преготовлен\n".formatted(order));
                return;
            }
            logger.info("%d заказ перемещен на склад\n".formatted(order));
        }
    }
}
