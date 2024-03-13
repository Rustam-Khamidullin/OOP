package ru.nsu.khamidullin.pizza;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The {@code BlockingQueue} class represents a thread-safe blocking queue implementation.
 * It uses explicit locks and conditions to ensure safe and efficient multi-threaded access to the underlying queue.
 *
 * @param <T> The type of elements stored in the queue.
 */
public class BlockingQueue<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private Queue<T> queue = new LinkedList<>();
    private final int capacity;

    /**
     * Constructs a blocking queue with the default maximum capacity (Integer.MAX_VALUE).
     */
    public BlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Constructs a blocking queue with the specified maximum capacity.
     *
     * @param capacity The maximum capacity of the blocking queue.
     */
    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds an element to the queue. Blocks if the queue is full.
     *
     * @param value The element to be added to the queue.
     * @throws InterruptedException If the operation is interrupted while waiting.
     */
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

    /**
     * Removes and returns an element from the queue. Blocks if the queue is empty.
     *
     * @return The element removed from the queue.
     * @throws InterruptedException If the operation is interrupted while waiting.
     */
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

    /**
     * Checks if the queue is empty.
     *
     * @return {@code true} if the queue is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        lock.lock();
        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Sets the internal queue to the specified queue.
     *
     * @param queue The new queue to set.
     */
    public void setQueue(Queue<T> queue) {
        lock.lock();
        try {
            this.queue = queue;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets the internal queue.
     *
     * @return The internal queue.
     */
    public Queue<T> getQueue() {
        lock.lock();
        try {
            return queue;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets the maximum capacity of the blocking queue.
     *
     * @return The maximum capacity of the blocking queue.
     */
    public int getCapacity() {
        return capacity;
    }
}