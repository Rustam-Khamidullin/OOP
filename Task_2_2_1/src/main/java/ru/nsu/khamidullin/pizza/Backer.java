package ru.nsu.khamidullin.pizza;

public class Backer implements Runnable {
    private final BlockingQueue<Integer> orders;
    private final BlockingQueue<Integer> storage;
    private final int cookingTime;
    private boolean isRunning;

    public Backer(int cookingTime, BlockingQueue<Integer> orders, BlockingQueue<Integer> storage) {
        this.cookingTime = cookingTime;
        this.orders = orders;
        this.storage = storage;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning || !orders.isEmpty()) {
            try {
                int order = orders.pop();
                System.out.printf("%d заказ готовится\n", order);

                Thread.sleep(cookingTime);

                storage.push(order);
                System.out.printf("%d заказ перемещен на склад\n", order);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopWorking() {
        this.isRunning = false;
    }
}
