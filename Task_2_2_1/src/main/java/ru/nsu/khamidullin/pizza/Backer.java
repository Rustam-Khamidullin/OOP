package ru.nsu.khamidullin.pizza;

public class Backer implements Runnable {
    private final BlockingQueue<Integer> orders;
    private final BlockingQueue<Integer> storage;
    private final int cookingTime;

    public Backer(int cookingTime, BlockingQueue<Integer> orders, BlockingQueue<Integer> storage) {
        this.cookingTime = cookingTime;
        this.orders = orders;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
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
}
