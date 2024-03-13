package ru.nsu.khamidullin.pizza;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public BlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void push(T value) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();
            }
            queue.add(value);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T pop() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            T value = queue.poll();
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public void setQueue(Queue<T> queue) {
        lock.lock();
        try {
            this.queue = queue;
        } finally {
            lock.unlock();
        }
    }

    public Queue<T> getQueue() {
        lock.lock();
        try {
            return queue;
        } finally {
            lock.unlock();
        }
    }

    public int getCapacity() {
        return capacity;
    }
}
