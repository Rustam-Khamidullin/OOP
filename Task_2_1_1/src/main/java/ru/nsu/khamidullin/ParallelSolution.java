package ru.nsu.khamidullin;

import static ru.nsu.khamidullin.PrimeChecker.isPrime;

/**
 * The {@code ParallelSolution} class provides a parallelized method for finding prime numbers
 * within an array of integers using multiple threads.
 */
public class ParallelSolution {
    /**
     * Finds if there is a prime number in the given array using multiple threads.
     *
     * @param array         an array of integers
     * @param threadNumber  the number of threads to be used
     * @return {@code true} if a prime number is found, {@code false} otherwise
     * @throws IllegalArgumentException if the specified thread number is less than or equal to zero
     */
    public static boolean findPrimeParallel(int[] array, int threadNumber) {
        if (threadNumber <= 0) {
            throw new IllegalArgumentException("Incorrect thread number");
        }

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

        boolean result = false;
        try {
            for (int i = 0; i < threadNumber; i++) {
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
        boolean result = false;
        int[] arr;

        public FindPrimeTask(int[] arr, int start, int end) {
            this.start = start;
            this.end = end;
            this.arr = arr;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (isPrime(arr[i])) {
                    result = true;
                    return;
                }
            }
        }
    }
}
