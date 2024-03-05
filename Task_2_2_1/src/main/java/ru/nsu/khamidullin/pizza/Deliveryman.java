package ru.nsu.khamidullin.pizza;

public class Deliveryman implements Runnable {
    private static final int DELIVERY_TIME = 5000;
    private final BlockingQueue<Integer> storage;
    private final int capacity;

    public Deliveryman(int capacity, BlockingQueue<Integer> storage) {
        this.capacity = capacity;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int current = 0;
                do {
                    int id = storage.pop();
                    System.out.printf("Заказ %d доставляется\n", id);
                    current++;
                } while (current < capacity || storage.size() != 0);

                Thread.sleep(DELIVERY_TIME);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

