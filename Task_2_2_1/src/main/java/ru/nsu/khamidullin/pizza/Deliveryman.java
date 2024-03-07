package ru.nsu.khamidullin.pizza;

public class Deliveryman implements Runnable {
    private static final int DELIVERY_TIME = 5000;
    private final BlockingQueue<Integer> storage;
    private final int capacity;
    private boolean isRunning;

    public Deliveryman(int capacity, BlockingQueue<Integer> storage) {
        this.capacity = capacity;
        this.storage = storage;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning || !storage.isEmpty()) {
            try {
                int current = 0;
                do {
                    int id = storage.pop();
                    System.out.printf("Заказ %d доставляется\n", id);
                    current++;
                } while (current < capacity && !storage.isEmpty());

                Thread.sleep(DELIVERY_TIME);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopWorking() {
        this.isRunning = false;
    }
}

