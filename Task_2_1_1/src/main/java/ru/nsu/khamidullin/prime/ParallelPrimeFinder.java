package ru.nsu.khamidullin.prime;

import java.util.Arrays;

/**
 * The {@code ParallelPrimeFinder} class provides a parallelized method for finding prime numbers
 * within an array of integers using multiple threads.
 * It extends the {@link PrimeFinder} abstract class.
 */
public class ParallelPrimeFinder extends PrimeFinder {

    private final int threadNumber;

    /**
     * Constructs a {@code ParallelPrimeFinder} with the specified number of threads.
     *
     * @param threadNumber the number of threads to be used for parallelized prime finding.
     * @throws IllegalArgumentException if the specified number of threads is less than or
     * equal to zero.
     */
    public ParallelPrimeFinder(int threadNumber) {
        if (threadNumber <= 0) {
            throw new IllegalArgumentException("Thread number must be greater than zero");
        }

        this.threadNumber = threadNumber;
    }

    /**
     * Checks if there is at least one prime number in the specified array using parallelized
     * threads.
     *
     * @param array an array of integers to be checked for the presence of prime numbers.
     * @return {@code true} if at least one prime number is present, {@code false} otherwise.
     */
    @Override
    public boolean hasPrime(int[] array) {
        int blockSize = array.length / threadNumber;
        int start = 0;
        int end = blockSize;

        var threads = new Thread[threadNumber];
        var tasks = new FindPrimeTask[threadNumber];
        for (int i = 0; i < threadNumber; i++) {
            var newTask = new FindPrimeTask(array, start, end);
            Thread newThread = new Thread(newTask);
            newThread.start();

            tasks[i] = newTask;
            threads[i] = newThread;

            start = end;
            end = i == threadNumber - 2 ? array.length : end + blockSize;
        }

        return waitThreadsCompletion(threads, tasks);
    }

    private boolean waitThreadsCompletion(Thread[] threads, FindPrimeTask[] tasks) {
        boolean result = false;

        try {
            for (int i = 0; i < threads.length; i++) {
                if (!result) {
                    threads[i].join();
                    if (tasks[i].result) {
                        result = true;
                    }
                } else {
                    threads[i].interrupt();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Represents the task assigned to each thread for finding prime numbers.
     */
    static class FindPrimeTask implements Runnable {
        int start;
        int end;
        boolean result;
        int[] arr;

        /**
         * Constructs a {@code FindPrimeTask} with the specified array and range.
         *
         * @param arr   the array to be checked for prime numbers.
         * @param start the starting index of the array range.
         * @param end   the ending index of the array range.
         */
        public FindPrimeTask(int[] arr, int start, int end) {
            this.start = start;
            this.end = end;
            this.arr = arr;
        }

        @Override
        public void run() {
            result = Arrays.stream(arr, start, end)
                    .anyMatch(PrimeFinder::isPrime);
        }
    }
}